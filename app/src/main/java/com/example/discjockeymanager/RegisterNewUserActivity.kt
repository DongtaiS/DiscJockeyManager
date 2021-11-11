package com.example.discjockeymanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.FieldClassification
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.example.discjockeymanager.databinding.ActivityRegisterNewUserBinding
import org.json.JSONObject
import java.util.regex.Pattern

class RegisterNewUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterNewUserBinding
    val emailPattern = ""
    private val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
    private val numberPattern = "^(?=.*[0-9])"
    private val lowerCasePattern = "^(?=.*[a-z])"
    private val upperCasePattern = "^(?=.*[A-Z])"
    private val specialCharacterPattern = "^(?=.*[!@#$%&*()])"

    private var validUsername = false
    private var validEmail = false
    private var validPass = false
    private var confirmedPass = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterNewUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            val textObserver = object: TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //ignore
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //ignore
                }

                override fun afterTextChanged(p0: Editable?) {
                    validateInput(
                        editTextRegisterUsername.text.toString(),
                        editTextRegisterEmail.text.toString(),
                        editTextRegisterPassword.text.toString(),
                        editTextRegisterConfirmPass.text.toString())
                }

            }
            editTextRegisterPassword.addTextChangedListener(textObserver)
            editTextRegisterConfirmPass.addTextChangedListener(textObserver)
            editTextRegisterEmail.addTextChangedListener(textObserver)
            editTextRegisterUsername.addTextChangedListener(textObserver)

            buttonRegisterTryRegister.setOnClickListener {
                if (validUsername && validEmail && validPass && confirmedPass) {
                    //Makes an API request to register a new user
                    val params = JSONObject()
                    params.put("username", editTextRegisterUsername.text)
                    params.put("email", editTextRegisterEmail.text)
                    params.put("password", editTextRegisterPassword.text)

                    APIRequestHelper.jsonRequest(this@RegisterNewUserActivity, RequestType.REGISTER, params, {
                        //Callback creates a toast message and returns user to sign in page
                        Log.i("REGISTER", it.toString())
                        Toast.makeText(this@RegisterNewUserActivity, "Successfully Registered!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@RegisterNewUserActivity, MainActivity::class.java))
                    }, {
                        it.printStackTrace()
                        val errorMessage = JSONObject(String(it.networkResponse.data)).getJSONObject("error")
                        val usernameError = errorMessage.getString("username")
                        val emailError = errorMessage.getString("email")
                        binding.textRegisterError.text = usernameError + emailError
                    })
                } else {
                    with(binding) {
                        if (!validUsername) editTextRegisterUsername.error = "Enter a valid username."
                        if (!validEmail) editTextRegisterEmail.error = "Enter a valid email address."
                        if (!validPass) editTextRegisterPassword.error = "Enter a valid password."
                        if (!confirmedPass) editTextRegisterConfirmPass.error = "Enter your password again."

                    }
                }
            }
        }
    }
    fun validateInput(username: String, email: String, password: String, confirmPass: String) {
        validUsername = validateUsername(username)
        validEmail = validateEmail(email)
        validPass = validatePassword(password)
        confirmedPass = confirmPassword(password, confirmPass)
    }
    private fun validateEmail(email: String): Boolean {
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true
        } else if (email.isNotBlank()) {
            binding.editTextRegisterEmail.error = "Please enter a valid email address."
        }
        return false
    }
    private fun validatePassword(password: String): Boolean {
        //Checks if password contains required characters
        val containsNum = Regex(numberPattern).find(password) != null
        val containsUppercase = Regex(upperCasePattern).find(password) != null
        val containsLowercase = Regex(lowerCasePattern).find(password) != null
        val containsSpecialChar = Regex(specialCharacterPattern).find(password) != null
        val lengthCheck = password.length >= 8
        if (containsLowercase && containsNum && containsSpecialChar && containsUppercase && lengthCheck) {
            return true
        } else if (password.isNotBlank()) {
            with (binding) {
                //Depending on which requirements are missing, changes error message
                editTextRegisterPassword.error = ""
                if (!containsNum) editTextRegisterPassword.error = "Missing a number. "
                if (!containsUppercase) editTextRegisterPassword.error = editTextRegisterPassword.error.toString() + "Missing an uppercase character. "
                if (!containsLowercase) editTextRegisterPassword.error = editTextRegisterPassword.error.toString() + "Missing a lowercase character. "
                if (!containsSpecialChar) editTextRegisterPassword.error = editTextRegisterPassword.error.toString() + "Missing a special character. "
                if (!lengthCheck) editTextRegisterPassword.error = editTextRegisterPassword.error.toString() + "Password is less than 8 characters."
            }
        }
        return false
    }
    private fun confirmPassword(password: String, confirmPass: String): Boolean {
        if (password == confirmPass) {
            return true
        } else if (confirmPass.isNotBlank()) {
            binding.editTextRegisterConfirmPass.error = "Your passwords do not match."
        }
        return false
    }
    private fun validateUsername(username: String) : Boolean {
        return username.isNotBlank()
    }
}