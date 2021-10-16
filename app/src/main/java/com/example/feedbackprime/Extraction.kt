package com.example.feedbackprime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
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

        sendAppId()
    }

    private fun sendAppId() {
        val parameters = JSONObject()
        parameters.put("type", "application")
        parameters.put("appId", "316d68796574616370626830674c713457436241795068556a7277523853626e")
        parameters.put(
            "appSecret",
            "57546179597542416a4e324d45542d6c543176394a48344d65564557613068434f726f426c6f627744787365746c304d654b6843377147334b75736b34574277"
        )

        val req = JsonObjectRequest(Request.Method.POST, newUrl, parameters,
            {
                accessToken = it.getString("expiresIn").toString()
            }, {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            })
        MySingleton.getInstance(this).addToRequestQueue(req)

        binding.access.text = accessToken
    }
}