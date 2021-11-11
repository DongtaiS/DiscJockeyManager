package com.example.discjockeymanager

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.discjockeymanager.databinding.ActivityForgotPasswordBinding
import org.json.JSONObject

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonPassResetReset.setOnClickListener {
            val params = JSONObject()
            params.put("email", binding.editTextPassResetEmail.text)
            APIRequestHelper.jsonRequest(this@ForgotPasswordActivity, RequestType.RESET_PASS_TOKEN, params, {
                Log.i("RESET", it.toString())
            })
        }
    }
}