package com.example.feedbackprime.callprocess.inputs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.feedbackprime.R
import com.example.feedbackprime.databinding.FragmentInputUrlBinding

class InputUrl : Fragment(R.layout.fragment_input_url) {
    private lateinit var binding: FragmentInputUrlBinding
    var Url: String? = null
    var Name: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("InputURL", "Inside Input URL")
        binding = FragmentInputUrlBinding.inflate(inflater, container, false)

        binding.submitButton.setOnClickListener {
            Url = binding.inputMediaUrlEditText.text.toString()
            Name = binding.inputNameOfVideo.text.toString()

            //setting errors
            if (Url.isNullOrEmpty()) {
                binding.inputMediaUrlEditText.error = "URL can't be empty"
            } else if (Name.isNullOrEmpty()) {
                binding.inputNameOfVideo.error = "Name can't be empty"
            } else {
                val action = InputUrlDirections.actionInputUrlToProcessInfo(Url!!, Name!!)
                findNavController().navigate(action)
            }
        }

        return binding.root
    }
}