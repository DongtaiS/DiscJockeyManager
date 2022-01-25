package com.example.discjockeymanager.signin


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.discjockeymanager.*
import com.example.discjockeymanager.mainpages.HomepageActivity
import com.example.discjockeymanager.databinding.ActivityMainBinding
import org.json.JSONObject
/**
 * Sign-in page for users, default page after splash screen
 */

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        //Refreshes authtoken and attempts automatic sign-in on startup using cached refresh token, if not null or empty
        val token = SharedPreferenceHelper.getRefreshToken(this@MainActivity)
        if (!token.isNullOrBlank()) {
            val params = JSONObject()
            params.put("refreshToken", token)
            params.put("token", SharedPreferenceHelper.getAccessToken(this@MainActivity))

            APIRequestHelper.jsonRequest(this@MainActivity, RequestType.VALIDATE_TOKEN, params, {
                SharedPreferenceHelper.setAuthTokens(this@MainActivity, it.getString("accessToken"), SharedPreferenceHelper.getRefreshToken(this@MainActivity)!!)

                //Upon successful refresh of auth token, retrieve cached user data and assign as current user, if not null or empty
                val userJsonString = SharedPreferenceHelper.getUserJSON(this@MainActivity)
                if (!userJsonString.isNullOrBlank()) {
                    LoggedInUser.currentUser = LoggedInUser.parseJSON(JSONObject(userJsonString))

                    //Navigate to homepage dashboard
                    startActivity(Intent(this@MainActivity, HomepageActivity::class.java))
                }
            }) {
                //If request fails, prints error message
                Log.i("TESTTOKEN", it.toString())
                it.printStackTrace()
            }
        }

        //On button press, makes an API request to verify log in information
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

                //Cache user data and tokens, and navigate to homepage dashboard
                startActivity(Intent(this@MainActivity, HomepageActivity::class.java))
                SharedPreferenceHelper.setUserJSON(this@MainActivity, userData.toString())
                SharedPreferenceHelper.setAuthTokens(
                    this@MainActivity,
                    it.getString("accessToken"),
                    it.getString("refreshToken")
                )
            }) {
                //If request fails, displays error message to user
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