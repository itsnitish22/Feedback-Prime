package com.example.feedbackprime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.feedbackprime.databinding.ActivityUrlAcceptBinding

class UrlAccept : AppCompatActivity() {

    private lateinit var binding: ActivityUrlAcceptBinding
    private lateinit var token:String
    private lateinit var url:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUrlAcceptBinding.inflate(layoutInflater)
        setContentView(binding.root)
        token= intent.extras?.getString("accessToken").toString()
        url = binding.editTextTextPersonName.text.toString()
        Log.i("UrlAccept",token)
        val name=binding.name.text.toString()
        val url = binding.editTextTextPersonName.text.toString()
        val name = binding.name.text.toString()
        binding.submitButton.setOnClickListener {
            val intent = Intent(this, VideoProcess::class.java)
            intent.putExtra("url", url)
            intent.putExtra("name",name)
            intent.putExtra("accessToken",token)
            startActivity(intent)
        }

    }


}