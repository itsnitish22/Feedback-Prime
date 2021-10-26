package com.example.feedbackprime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.feedbackprime.databinding.ActivityExtractionBinding

class Extraction : AppCompatActivity() {
    private val newUrl = "https://api.symbl.ai/oauth2/token:generate"
    private var accessToken: String = ""
    lateinit var binding: ActivityExtractionBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExtractionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.i("Extraction", "logging")
    }
}