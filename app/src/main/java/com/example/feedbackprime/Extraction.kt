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
    private val newUrl = "https://api.symbl.ai/oauth2/token:generate"
    private var accessToken: String=""
    lateinit var binding: ActivityExtractionBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExtractionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sendAppId()

        Log.i("Extraction","logging")
//        Log.d("Extraction",hello)


    }

    private fun sendAppId(): String {
        val parameters = JSONObject()
        parameters.put("type", "application")
        parameters.put("appId", "706f657063505776757476457764344d7434674553644c53747a4d5757795156")
        parameters.put(
            "appSecret",
            "39594b715047596d51705a79704879786e526e52344a613676794659766142633859596e4c597975706263684c73483577684b67334a44313348784668436f57"
        )
        Log.i("Extraction", "API called second")

        val queue = Volley.newRequestQueue(this)
        val req = JsonObjectRequest(Request.Method.POST, newUrl, parameters,
            {
                accessToken = it.getString("accessToken")
                val intent= Intent(this,UrlAccept::class.java)
                intent.putExtra("accessToken",accessToken)
                startActivity(intent)
//
                Log.i("Extraction", accessToken)
            }, {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            })

        queue.add(req)

    }
}