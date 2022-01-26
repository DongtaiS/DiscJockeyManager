package com.example.discjockeymanager.signin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.discjockeymanager.APIRequestHelper
import com.example.discjockeymanager.RequestType
import com.example.discjockeymanager.databinding.ActivityForgotPasswordBinding
import org.json.JSONObject

/**
 *  Activity to help user reset their password
 */

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //When button is tapped, makes an API call to send an email to reset password
        binding.buttonPassResetReset.setOnClickListener {

            val params = JSONObject()
            params.put("email", binding.editTextPassResetEmail.text)

            APIRequestHelper.jsonRequest(
                this@ForgotPasswordActivity,
                RequestType.RESET_PASS_TOKEN,
                params,
                {
                    Log.i("RESET", it.toString())
                })
        }

        //When back button is pressed, return to previous page
        binding.imgBtnPassResetBack.setOnClickListener {
            finish()
        }
    }
}