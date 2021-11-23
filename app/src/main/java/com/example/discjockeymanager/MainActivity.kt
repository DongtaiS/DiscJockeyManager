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

        Log.i("TESTTOKEN", SharedPreferenceHelper.getUserName(this@MainActivity)!!)

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
                val ability = userData.getJSONArray("ability").getJSONObject(0)
                val user = User(userData.getInt("id"),
                    userData.getString("fullName"), userData.getString("company"), userData.getString("role"),
                    userData.getString("username"), userData.getString("country"), userData.getString("contact"),
                    userData.getString("email"), userData.getString("currentPlan"), userData.getString("status"),
                    userData.getString("avatar"), ability.getString("action"), ability.getString("subject"))
                LoggedInUser.currentUser = user
                startActivity(Intent(this@MainActivity, HomepageActivity::class.java))
                SharedPreferenceHelper.setUserName(this@MainActivity, user.username)
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