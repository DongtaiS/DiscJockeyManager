package com.example.discjockeymanager

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.util.*
import com.android.volley.AuthFailureError


//Enum representing the possible API requests
enum class RequestType {
    LOGIN, REGISTER, UPDATE_PASS, RESET_PASS_TOKEN,
    VALIDATE_TOKEN, GET_EVENTS, GET_CLIENTS, GET_SERVICES,
    GET_RESOURCES, GET_SYSTEMS, GET_VENUES
}

//This class is used to make API requests
class APIRequestHelper {
    companion object {
        private const val baseUrl = "https://api.discjockeymanager.com/api/"
        //Map of RequestTypes to their respective url
        private val requests = mapOf(
            RequestType.LOGIN to "auth/login.php",
            RequestType.REGISTER to "auth/register.php",
            RequestType.UPDATE_PASS to "auth/update-forgot-password.php",
            RequestType.RESET_PASS_TOKEN to "auth/password-reset-token.php",
            RequestType.VALIDATE_TOKEN to "auth/validate-token.php",
            RequestType.GET_EVENTS to "user/event/events.php",
            RequestType.GET_CLIENTS to "user/client/clients.php",
            RequestType.GET_SERVICES to "user/service/services.php",
            RequestType.GET_RESOURCES to "user/resource/resources.php",
            RequestType.GET_SYSTEMS to "user/system/systems.php",
            RequestType.GET_VENUES to "user/venue/venues.php"
        )

        //API request that calls the onComplete function with the returned JSONObject as its parameter, or onError if there is an error
        fun jsonRequest(context: Context, type: RequestType, params: JSONObject, onComplete: (JSONObject) -> Unit,
                        onError: (VolleyError) -> Unit = { it.printStackTrace() }) {
            val queue = Volley.newRequestQueue(context)
            val request = JsonObjectRequest(Request.Method.POST, "$baseUrl${requests[type]}", params, {
                onComplete(it)
            }, {
                onError(it)
            })
            queue.add(request)
        }

        fun jsonRequestWithAuth(context: Context, type: RequestType, params: JSONObject, authToken: String, onComplete: (JSONObject) -> Unit,
                                onError: (VolleyError) -> Unit = { it.printStackTrace() }) {
            val request = object : JsonObjectRequest(
                Request.Method.GET, "$baseUrl${requests[type]}", params, {
                    onComplete(it)
                },
                {
                    onError(it)
                }) {

                //This is for Headers If You Needed
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headerParams: MutableMap<String, String> = HashMap()
                    headerParams["Content-Type"] = "application/json; charset=UTF-8"
                    headerParams["Authorization"] = "Bearer ${SharedPreferenceHelper.getAccessToken(context)}"
                    return headerParams
                }
            }
            val requestQueue = Volley.newRequestQueue(context)
            requestQueue.add(request)
        }

        fun refreshAuthToken(context: Context, params: JSONObject, onComplete: (JSONObject) -> Unit,
                                onError: (VolleyError) -> Unit = { it.printStackTrace(); Log.i("TESTTOKEN", String(it.networkResponse.data)) }) {
            val request = object : JsonObjectRequest(
                Request.Method.GET, "$baseUrl${requests[RequestType.VALIDATE_TOKEN]}", params, {
                    onComplete(it)
                },
                {
                    onError(it)
                }) {

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headerParams: MutableMap<String, String> = HashMap()
                    headerParams["Content-Type"] = "application/json; charset=UTF-8"
                    headerParams["RefreshToken"] = "${SharedPreferenceHelper.getRefreshToken(context)}"
                    return headerParams
                }
            }
            val requestQueue = Volley.newRequestQueue(context)
            requestQueue.add(request)
        }

        fun getErrorJSONObject(error: VolleyError) : JSONObject {
            return JSONObject(String(error.networkResponse.data)).getJSONObject("error")
        }
    }
}