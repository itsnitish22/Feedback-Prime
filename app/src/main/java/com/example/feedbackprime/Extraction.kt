package com.example.feedbackprime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.feedbackprime.databinding.ActivityExtractionBinding
import org.json.JSONObject

class Extraction : AppCompatActivity() {
//    private val url = intent.extras?.getString("url")
    private var accessToken: String = ""
    lateinit var binding: ActivityExtractionBinding

    private val newUrl = "https://api.symbl.ai/oauth2/token:generate"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExtractionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = intent.extras?.getString("url")
        val name=intent.extras?.getString("name")

        sendAppId()
        val intent= Intent(this,VideoProcess::class.java)
        intent.putExtra("url",url)
        intent.putExtra("name",name)
        intent.putExtra("token",accessToken)
        startActivity(intent)


    }

    private fun sendAppId() {
        Log.i("Extraction", "API called")

        val parameters = JSONObject()
        parameters.put("type", "application")
        parameters.put("appId", "706f657063505776757476457764344d7434674553644c53747a4d5757795156")
        parameters.put(
            "appSecret",
            "39594b715047596d51705a79704879786e526e52344a613676794659766142633859596e4c597975706263684c73483577684b67334a44313348784668436f57"
        )

        val queue = Volley.newRequestQueue(this)
        val req = JsonObjectRequest(Request.Method.POST, newUrl, parameters,
            {
                Log.i("Extraction", "API called second")
//                val temp = it.getJSONObject("")
                accessToken = it.getString("accessToken")
                Toast.makeText(this,"Token generated",Toast.LENGTH_SHORT).show()
                Log.i("Extraction",accessToken)
            }, {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            })

        queue.add(req)
//        MySingleton.getInstance(this).addToRequestQueue(req)

    }
}