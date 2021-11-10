package com.example.discjockeymanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.discjockeymanager.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.buttonLoginSignIn.setOnClickListener {
            val params = JSONObject()
            params.put("email", binding.editTextLoginEmail.text)
            params.put("password", binding.editTextLoginPassword.text)
            APIRequestHelper.JSONRequest(this@MainActivity, APIRequestHelper.RequestType.LOGIN, params) {
                Log.i("REQUEST", it.toString())
                Toast.makeText(this@MainActivity, "Logged in!", Toast.LENGTH_SHORT).show()
                //TODO: add sign-in functionality
            }
        }
        binding.buttonLoginRegister.setOnClickListener {
            startActivity(Intent(this@MainActivity, RegisterNewUserActivity::class.java))
        }
        binding.buttonLoginForgotPass.setOnClickListener {
            startActivity(Intent(this@MainActivity, ForgotPasswordActivity::class.java))
        }
        setContentView(binding.root)
    }
}