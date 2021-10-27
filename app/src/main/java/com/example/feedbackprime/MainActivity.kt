package com.example.feedbackprime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.sawolabs.androidsdk.Sawo
import com.sawolabs.androidsdk.LOGIN_SUCCESS_MESSAGE


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginButton: Button = findViewById(R.id.login)
        loginButton.setOnClickListener {
            onClickLogin()
        }
        val message = intent.getStringExtra(LOGIN_SUCCESS_MESSAGE)
    }

    private fun onClickLogin() {
        Sawo(
            this,
            "68348a0e-da16-4e57-8f03-4daceb1bce0c\n", // your api key
            "615de8b2956fa888e2ab633duDIlH0mAzkPfNy0cpddvHt63"  // your api key secret
        ).login(
            "email", // can be one of 'email' or 'phone_number_sms'
            UrlAccept::class.java.name // Callback class name
        )
    }
}