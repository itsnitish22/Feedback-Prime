package com.example.feedbackprime

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.feedbackprime.databinding.ActivityVideoProcessBinding
import org.json.JSONObject

class VideoProcess : AppCompatActivity() {
    lateinit var binding: ActivityVideoProcessBinding
    lateinit var accessToken: String
    lateinit var url: String
    lateinit var name: String
    lateinit var conv: String
    lateinit var jobId: String
    lateinit var sentimentAnalysisUrl: String

    private val convIdUrl =
        "https://api.symbl.ai/v1/process/video/url?enableSpeakerDiarization=true&diarizationSpeakerCount=2"
    private val tokenGenerateUrl = "https://api.symbl.ai/oauth2/token:generate"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoProcessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        url = intent.extras?.getString("url").toString()
        Log.i("VideoProcess", url)
        name = intent.extras?.getString("name").toString()
        Log.i("VideoProcess", name)

        sendAppId()
    }

    private fun sendAppId() {
        val parameters = JSONObject()
        parameters.put("type", "application")
        parameters.put("appId", "706f657063505776757476457764344d7434674553644c53747a4d5757795156")
        parameters.put(
            "appSecret",
            "39594b715047596d51705a79704879786e526e52344a613676794659766142633859596e4c597975706263684c73483577684b67334a44313348784668436f57"
        )

        val queue = Volley.newRequestQueue(this)
        val req = JsonObjectRequest(Request.Method.POST, tokenGenerateUrl, parameters,
            {
                accessToken = it.getString("accessToken")
                getConvID()

            }, {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            })
        queue.add(req)
    }

    private fun getConvID() {
        val body = JSONObject()
        body.put("url", url)
        body.put("name", name)
        val queue = Volley.newRequestQueue(this)
        val req = object : JsonObjectRequest(
            Method.POST, convIdUrl, body,
            {

                //getting the conversation id
                conv = it.getString("conversationId")
                jobId = it.getString("jobId")

                Log.i("VideoProcess", conv)
                Log.i("VideoProcess", jobId)

                sentimentAnalysisUrl =
                    "https://api.symbl.ai/v1/conversations/$conv/messages?sentiment=true"

                Log.i("VideoProcess", sentimentAnalysisUrl)

                getStatus(jobId, sentimentAnalysisUrl)
            }, {
                Toast.makeText(this, "Error in video", Toast.LENGTH_SHORT).show()
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Authorization", "Bearer $accessToken")
                headers.put("Content-Type", "application/json")
                return headers
            }
        }
        queue.add(req)
    }

    fun getStatus(jobid: String, sentiment: String) {

        val progressBar: ProgressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE

//        Toast.makeText(this, "Please wait while the video is being analysed", Toast.LENGTH_SHORT)
//            .show()

        val endPt = "https://api.symbl.ai/v1/job/$jobid"
        val queue = Volley.newRequestQueue(this)
        val request = object : JsonObjectRequest(
            Method.GET, endPt, null, {
                val status = it.getString("status")
                if (status == "completed") {
                    progressBar.visibility = View.GONE
                    getResponse(sentiment)
                } else {
                    val handler = Handler(Looper.getMainLooper())
                    Handler().postDelayed({
                        getStatus(jobid, sentiment)
                    }, 500)
                }
            }, {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Authorization", "Bearer $accessToken")
                return headers
            }
        }
        queue.add(request)
    }

    fun getResponse(sentimentUrl: String) {
        val queue = Volley.newRequestQueue(this)
        val req = object : JsonObjectRequest(
            Method.GET, sentimentUrl, null,
            {
                Log.i("VideoProcess", "Response generated")
                Log.i("VideoProcess", it.toString())
                showResult(it)
            }, {
                Toast.makeText(this, "Error in video", Toast.LENGTH_SHORT).show()
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Authorization", "Bearer $accessToken")
                return headers
            }
        }
        queue.add(req)
    }

    fun showResult(response: JSONObject) {
        Log.i("response", response.toString())
        // getting the recyclerview by its id
        val recyclerview = binding.recyclerview
//        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)
//        // ArrayList of class ItemsViewModel
        val data = ArrayList<ItemsViewModel>()
        val dataArray = response.getJSONArray("messages")
        Log.i("Array", dataArray.toString())
        var scoreavg: Double = 0.0
        Log.i("Length", dataArray.length().toString())
//
        for (i in 0 until dataArray.length()) {
//            Log.i("Index",i.toString())
            val obj = dataArray.getJSONObject(i)
            val text = obj.getString("text")
            Log.i("Text", text.toString())
            val speaker = obj.getJSONObject("from").getString("name")
            Log.i("Text", speaker.toString())
            val score = obj.getJSONObject("sentiment").getJSONObject("polarity").getString("score")
                .toDouble()
            var icon = R.drawable.sad
            scoreavg += score
            if (score > -0.3 && score <= -1.0)
                icon = R.drawable.sad
            else if (score >= -0.3 && score <= 0.3)
                icon = R.drawable.neutral
            else if (score > 0.3 && score <= 1.0)
                icon = R.drawable.happy
            data.add(ItemsViewModel(text, speaker, icon))

        }
        scoreavg /= dataArray.length()
        // This will pass the ArrayList to our Adapter
        val adapter = CustomAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
    }
}
