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
    lateinit var token:String
    lateinit var url:String
    lateinit var name:String
    lateinit var conv:String
    private val requrl="https://api.symbl.ai/v1/process/video/url"
    lateinit var token: String
    lateinit var url: String
    lateinit var name: String
    lateinit var conv: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoProcessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.i("VideoProcess","HELLO")
        token=intent.extras?.getString("accessToken").toString()
        url=intent.extras?.getString("url").toString()
        name=intent.extras?.getString("name").toString()
        Log.i("VideoProcess","HELLO")
        Log.i("VideoProcess",name)

        getconvid()
        token = intent.extras?.getString("token").toString()
        url = intent.extras?.getString("url").toString()
        name = intent.extras?.getString("name").toString()

        getConversationId()
    }
    fun getconvid(){
        val body=JSONObject()
        body.put("url",url)
        Log.i("VideoProcess",url)
        body.put("name",name)
        val queue=Volley.newRequestQueue(this)
        val req = object:JsonObjectRequest(
            Method.POST,requrl, body,
            {
//                Toast.makeText(this,"Successful",Toast.LENGTH_SHORT).show()
                conv=it.getString("conversationId")
                Log.i("VideoProcess","Conversation API id extracted")
                Log.i("VideoProcess", "API called second")

    private fun getConversationId() {
        val body = JSONObject()
        body.put("url", url)
        body.put("name", name)
        val queue = Volley.newRequestQueue(this)

        val req = object : JsonObjectRequest(Method.POST, url, body,
            {
                conv = it.getString("conversationId")
                val text = binding.VPTextView
                text.text = conv
            }, {
                Toast.makeText(this, "Error in video", Toast.LENGTH_SHORT).show()
            })
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Authorization","Bearer $token")
                headers.put("Content-Type","application/json")
                headers["Content-Type"] = "application/json"
                headers["Authorization"] = "Bearer $token"
                return headers
            }
        }
        req.headers["Authorization"] = token
        queue.add(req)
    }
}