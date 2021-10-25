package com.example.feedbackprime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Request.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.feedbackprime.databinding.ActivityVideoProcessBinding
import org.json.JSONObject

class VideoProcess : AppCompatActivity() {
    lateinit var binding: ActivityVideoProcessBinding
    lateinit var token: String
    lateinit var url: String
    lateinit var name: String
    lateinit var conv: String

    private val requrl = "https://api.symbl.ai/v1/process/video/url"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoProcessBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        Log.i("VideoProcess", "HELLO")
        token = intent.extras?.getString("accessToken").toString()
        Log.i("VideoProcess", token)
        url = intent.extras?.getString("url").toString()
        Log.i("VideoProcess", url)
        name = intent.extras?.getString("name").toString()
        Log.i("VideoProcess", name)
//        Log.i("VideoProceocess", name)

        getconvid()

    }

    fun getconvid() {
        val body = JSONObject()
        body.put("url", url)
        Log.i("VideoProcess", url)
        body.put("name", name)
        val queue = Volley.newRequestQueue(this)
        val req = object : JsonObjectRequest(
            Method.POST, requrl, body,
            {
//                Toast.makeText(this,"Successful",Toast.LENGTH_SHORT).show()
                conv = it.getString("conversationId")
                Log.i("VideoProcess", conv)
                Log.i("VideoProcess", "Conversation API id extracted")
                Log.i("VideoProcess", "API called second")

            }, {
                Toast.makeText(this, "Error in video", Toast.LENGTH_SHORT).show()
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Authorization", "Bearer $token")
                headers.put("Content-Type", "application/json")
                return headers
            }
        }
//        req.headers.set("Authorization",token)
        queue.add(req)
    }

}