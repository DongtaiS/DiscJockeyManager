package com.example.discjockeymanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.discjockeymanager.databinding.ActivityRegisterNewUserBinding
import org.json.JSONObject

class RegisterNewUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterNewUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterNewUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            buttonRegisterTryRegister.setOnClickListener {
                if (editTextRegisterPassword.text.toString() != editTextRegisterConfirmPass.text.toString()) {
                    textRegisterError.text = "Your passwords do not match."
                    Log.i("REGISTER", "${editTextRegisterPassword.text} ${editTextRegisterConfirmPass.text} ${editTextRegisterPassword.text.equals(editTextRegisterConfirmPass.text)}")
                } else {
                    val params = JSONObject()
                    params.put("username", editTextRegisterUsername.text)
                    params.put("email", editTextRegisterEmail.text)
                    params.put("password", editTextRegisterPassword.text)

                    APIRequestHelper.JSONRequest(this@RegisterNewUserActivity, APIRequestHelper.RequestType.REGISTER, params) {
                        Log.i("REGISTER", it.toString())
                        Toast.makeText(this@RegisterNewUserActivity, "Successfully Registered!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@RegisterNewUserActivity, MainActivity::class.java))
                    }
                }
            }
        }
    }
}