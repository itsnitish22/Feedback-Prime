package com.example.feedbackprime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.feedbackprime.databinding.ActivityVideoProcessBinding
import org.json.JSONArray
import org.json.JSONObject

class VideoProcess : AppCompatActivity() {
    lateinit var binding: ActivityVideoProcessBinding
    lateinit var accessToken: String
    lateinit var url: String
    lateinit var name: String
    lateinit var conv: String

    private val ConvIdUrl = "https://api.symbl.ai/v1/process/video/url"
    private val TokenGenerateUrl = "https://api.symbl.ai/oauth2/token:generate"

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
//    val queue = Volley.newRequestQueue(this)
    private fun sendAppId() {
        val parameters = JSONObject()
        parameters.put("type", "application")
        parameters.put("appId", "706f657063505776757476457764344d7434674553644c53747a4d5757795156")
        parameters.put(
            "appSecret",
            "39594b715047596d51705a79704879786e526e52344a613676794659766142633859596e4c597975706263684c73483577684b67334a44313348784668436f57"
        )

        val queue = Volley.newRequestQueue(this)
        val req = JsonObjectRequest(Request.Method.POST, TokenGenerateUrl, parameters,
            {
                accessToken = it.getString("accessToken")
                getconvid()

            }, {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            })

        queue.add(req)

    }

    fun getconvid() {
        val body = JSONObject()
        body.put("url", url)
        Log.i("VideoProcess", url)
        body.put("name", name)
        val queue = Volley.newRequestQueue(this)
        val req = object : JsonObjectRequest(
            Method.POST, ConvIdUrl, body,
            {
                conv = it.getString("conversationId")
                Log.i("VideoProcess", conv)
                Log.i("VideoProcess", "Conversation API id extracted")
                Log.i("VideoProcess", "API called second")
                val SentimentAnalysisUrl = "https://api.symbl.ai/v1/conversations/$conv/messages?sentiment=true"
                Log.i("VideoProcess",SentimentAnalysisUrl)
                val handler = Handler(Looper.getMainLooper())
                Handler().postDelayed({
                    //doSomethingHere()
                                      getresponse(SentimentAnalysisUrl)
                }, 20000)


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

    fun getresponse(sentimenturl: String) {
        val queue = Volley.newRequestQueue(this)
        val req = object : JsonObjectRequest(
            Method.GET, sentimenturl, null,
            {
                Log.i("VideoProcess", "I am running")
//                val idf:JSONArray=it.getJSONArray("messages")
//                val temp:JSONObject=idf.getJSONObject(0)
//
//                val temp2=temp.getString("text").toString()
                Log.i("VideoProcess",it.toString())
            }, {
                Toast.makeText(this, "Error in video", Toast.LENGTH_SHORT).show()
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Authorization", "Bearer $accessToken")
                return headers
            }


//            override fun getParams(): MutableMap<String, String> {
//                val param = HashMap<String, String>()
//                param.put("sentiment", "true")
//                return param
//            }
        }
        queue.add(req)

    }

}