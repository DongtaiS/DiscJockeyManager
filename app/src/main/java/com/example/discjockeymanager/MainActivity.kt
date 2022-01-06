package com.example.discjockeymanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.discjockeymanager.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        APIRequestHelper.refreshAuthToken(this@MainActivity, JSONObject(),{
            Log.i("TESTTOKEN", "SUCCESS")
        }) {
            Log.i("TESTTOKEN", "FAILL")
        }
        //Refreshes authtoken on startup using cached refresh token
        val token = SharedPreferenceHelper.getRefreshToken(this@MainActivity)
        Log.i("TESTTOKEN", SharedPreferenceHelper.getRefreshToken(this@MainActivity) ?: "FAIL")
        if (!SharedPreferenceHelper.getRefreshToken(this@MainActivity).isNullOrBlank()) {
            val params = JSONObject()
            params.put("refreshToken", token)
            APIRequestHelper.jsonRequest(this@MainActivity, RequestType.VALIDATE_TOKEN, params, {
                SharedPreferenceHelper.setAuthTokens(this@MainActivity, it.getString("accessToken"), SharedPreferenceHelper.getRefreshToken(this@MainActivity)!!)
                Log.i("TESTTOKEN", it.toString())

                // Retrieve cached user data and assign as current user
                val userJsonString = SharedPreferenceHelper.getUserJSON(this@MainActivity)
                Log.i("INFO", userJsonString ?: "fail")
                if (!userJsonString.isNullOrBlank()) {
                    LoggedInUser.currentUser = LoggedInUser.parseJSON(JSONObject(userJsonString))
                    startActivity(Intent(this@MainActivity, HomepageActivity::class.java))
                }

            }) {
                Log.i("TESTTOKEN", it.toString())
                it.printStackTrace()
            }
        }

        //Makes an API request to verify log in information
        binding.buttonLoginSignIn.setOnClickListener {
            val params = JSONObject()
            params.put("email", binding.editTextLoginEmail.text)
            params.put("password", binding.editTextLoginPassword.text)

            APIRequestHelper.jsonRequest(this@MainActivity, RequestType.LOGIN, params, {
                Log.i("REQUEST", it.toString())
                Toast.makeText(this@MainActivity, "Logged in!", Toast.LENGTH_SHORT).show()

                //Gets returned JSONObject of user and creates a new User as the currentUser
                val userData = it.getJSONObject("userData")
                LoggedInUser.currentUser = LoggedInUser.parseJSON(userData)

                startActivity(Intent(this@MainActivity, HomepageActivity::class.java))
                SharedPreferenceHelper.setUserJSON(this@MainActivity, userData.toString())
                SharedPreferenceHelper.setAuthTokens(this@MainActivity, it.getString("accessToken"), it.getString("refreshToken"))
            }) {
                val error = APIRequestHelper.getErrorJSONObject(it)
                val errorMessage = error.getString("email")
                binding.textLoginError.text = errorMessage
                binding.textLoginError.visibility = View.VISIBLE
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

    override fun onResume() {
        binding.textLoginError.visibility = View.INVISIBLE
        super.onResume()
    }
}