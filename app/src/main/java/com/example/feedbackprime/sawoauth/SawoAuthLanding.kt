package com.example.feedbackprime.sawoauth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.feedbackprime.callprocess.ProcessCallActivity
import com.example.feedbackprime.databinding.FragmentSawoAuthLandingBinding
import com.sawolabs.androidsdk.Sawo

class SawoAuthLanding : Fragment() {
    private lateinit var binding: FragmentSawoAuthLandingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSawoAuthLandingBinding.inflate(inflater, container, false)

        binding.login.setOnClickListener {
            loginWithSawo()
        }

        return binding.root
    }

    private fun loginWithSawo() {
        Sawo(
            requireContext(),
            "68348a0e-da16-4e57-8f03-4daceb1bce0c\n", // your api key
            "615de8b2956fa888e2ab633duDIlH0mAzkPfNy0cpddvHt63"  // your api key secret
        ).login(
            "phone_number_sms", // can be one of 'email' or 'phone_number_sms'
            ProcessCallActivity::class.java.name // Callback class name
        )
    }
}