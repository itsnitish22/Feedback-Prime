package com.example.feedbackprime

import android.app.ProgressDialog
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyLog.TAG
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class Extraction : AppCompatActivity() {
    private val url = intent.extras?.getString("url")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extraction)
    }

    var volleyRequestQueue: RequestQueue? = null
    var dialog: ProgressDialog? = null
    val serverAPIURL: String? = url

    fun sendAppId() {
        val volleyRequestQueue = Volley.newRequestQueue(this)
        val dialog = ProgressDialog.show(this, "", "Please wait...", true)
        val parameters: MutableMap<String, String> = HashMap()

        parameters.put("type", "application")
        parameters.put("appId", "316d68796574616370626830674c713457436241795068556a7277523853626e")
        parameters.put(
            "appSecret",
            "57546179597542416a4e324d45542d6c543176394a48344d65564557613068434f726f426c6f627744787365746c304d654b6843377147334b75736b34574277"
        )
    }
}