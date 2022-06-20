package com.example.feedbackprime

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.feedbackprime.databinding.ActivityUrlAcceptBinding

class UrlAccept : AppCompatActivity() {

    private lateinit var binding: ActivityUrlAcceptBinding
    private lateinit var url: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUrlAcceptBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submitButton.setOnClickListener {
            Toast.makeText(this, "Submit button pressed", Toast.LENGTH_SHORT).show()
            url = binding.mediaUrlEditText.text.toString()
            val name = binding.nameEditText.text.toString()

            //setting errors
            if (TextUtils.isEmpty(url)) {
                binding.mediaUrlEditText.error = "URL can't be empty"
            } else if (TextUtils.isEmpty(name)) {
                binding.nameEditText.error = "Name can't be empty"
            } else {
                val intent = Intent(this, VideoProcess::class.java)
                intent.putExtra("url", url)
                intent.putExtra("name", name)
                startActivity(intent)
            }
        }
    }
}