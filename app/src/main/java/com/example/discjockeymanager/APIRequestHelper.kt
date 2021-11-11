package com.example.discjockeymanager

import android.content.Context
import android.util.Log
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.util.*

//Enum representing the possible API requests
enum class RequestType {
    LOGIN, REGISTER, UPDATE_PASS, RESET_PASS_TOKEN, VALIDATE_TOKEN
}

//This class is used to make API requests
class APIRequestHelper {
    companion object {
        private val baseUrl = "https://api.discjockeymanager.com/api/"
        //Map of RequestTypes to their respective url
        private val requests = mapOf(
            RequestType.LOGIN to "auth/login.php",
            RequestType.REGISTER to "auth/register.php",
            RequestType.UPDATE_PASS to "auth/update-forgot-password.php",
            RequestType.RESET_PASS_TOKEN to "auth/password-reset-token.php",
            RequestType.VALIDATE_TOKEN to "auth/validate-token.php"
        )

        //API request that calls the onComplete function with the returned JSONObject as its parameter, or onError if there is an error
        fun jsonRequest(context: Context, type: RequestType, params: JSONObject, onComplete: (JSONObject) -> Unit,
                        onError: (VolleyError) -> Unit = { it.printStackTrace() }) {
            val queue = Volley.newRequestQueue(context)
            val request = JsonObjectRequest(Request.Method.POST, "$baseUrl${requests[type]}", params, {
                onComplete(it, )
            }, {
                onError(it)
            })
            queue.add(request)
        }
    }
}