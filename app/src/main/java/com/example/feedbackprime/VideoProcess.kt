package com.example.feedbackprime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
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

    private val convIdUrl = "https://api.symbl.ai/v1/process/video/url"
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

        Log.i("VideoProcess", url)

        val queue = Volley.newRequestQueue(this)
        val req = object : JsonObjectRequest(
            Method.POST, convIdUrl, body,
            {

                //getting the conversation id
                conv = it.getString("conversationId")
                jobId = it.getString("jobId")

                Log.i("VideoProcess", conv)
                Log.i("VideoProcess", jobId)
                Log.i("VideoProcess", "Conversation API id extracted")
                Log.i("VideoProcess", "API called second")

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
        val endPt = "https://api.symbl.ai/v1/job/$jobid"
        val queue = Volley.newRequestQueue(this)
        val request = object : JsonObjectRequest(
            Method.GET, endPt, null, {
                val status = it.getString("status")
                if (status == "completed") {
                    getResponse(sentiment)
                } else {
                    val handler = Handler(Looper.getMainLooper())
                    Handler().postDelayed({
                        getStatus(jobid, sentiment)
                    }, 5000)
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
                Log.i("VideoProcess", "I am running")
                Log.i("VideoProcess", it.toString())
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
}