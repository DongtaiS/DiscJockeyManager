package com.example.discjockeymanager

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.util.*

class APIRequestHelper {
    enum class RequestType {
        LOGIN, REGISTER, UPDATE_PASS, RESET_PASS_TOKEN, VALIDATE_TOKEN
    }
    companion object {
        private val baseUrl = "https://api.discjockeymanager.com/api/"
        private val requests = mapOf(
            RequestType.LOGIN to "auth/login.php",
            RequestType.REGISTER to "auth/register.php",
            RequestType.UPDATE_PASS to "auth/update-forgot-password.php",
            RequestType.RESET_PASS_TOKEN to "auth/password-reset-token.php",
            RequestType.VALIDATE_TOKEN to "auth/validate-token.php"
        )
        fun JSONRequest(context: Context, type: RequestType, params: JSONObject, onComplete: (JSONObject) -> Unit) {
            val queue = Volley.newRequestQueue(context)
            val jsonRequest = JsonObjectRequest(Request.Method.POST, "$baseUrl${requests[type]}", params, {
                onComplete(it)
            }, {
                it.printStackTrace()
            })
            queue.add(jsonRequest)
        }
    }
}