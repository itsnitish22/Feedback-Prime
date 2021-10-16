package com.example.feedbackprime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.feedbackprime.databinding.ActivityUrlAcceptBinding

class UrlAccept : AppCompatActivity() {

    lateinit var binding: ActivityUrlAcceptBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUrlAcceptBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = binding.editTextTextPersonName.text.toString()
        val name=binding.name.text.toString()
        binding.submitButton.setOnClickListener {
            val intent = Intent(this, Extraction::class.java)
            intent.putExtra("url", url)
            intent.putExtra("name", name)
            startActivity(intent)
        }

    }


}