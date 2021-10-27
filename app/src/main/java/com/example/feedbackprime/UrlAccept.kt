package com.example.feedbackprime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.feedbackprime.databinding.ActivityUrlAcceptBinding

class UrlAccept : AppCompatActivity() {

    private lateinit var binding: ActivityUrlAcceptBinding
    private lateinit var url: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUrlAcceptBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submitButton.setOnClickListener {
            url = binding.editTextTextPersonNameEditText.text.toString()
            val name = binding.nameEditText.text.toString()
            val intent = Intent(this, VideoProcess::class.java)

            intent.putExtra("url", url)
            intent.putExtra("name", name)
            startActivity(intent)
        }
    }
}