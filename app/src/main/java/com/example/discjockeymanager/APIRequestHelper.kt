package com.example.discjockeymanager

import android.content.Context
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.util.*
import com.android.volley.AuthFailureError
/**
 *  Utility class that handles API requests to database
 */

//Enum representing the possible API requests
enum class RequestType {
    LOGIN, REGISTER, UPDATE_PASS, RESET_PASS_TOKEN,
    VALIDATE_TOKEN, GET_EVENTS, GET_CLIENTS, GET_SERVICES,
    GET_RESOURCES, GET_SYSTEMS, GET_VENUES, GET_STAFF, GET_SONGS,
    GET_ANALYTICS, GET_USER_INFO
}

class APIRequestHelper {

    companion object {
        private const val baseUrl = "https://api.discjockeymanager.com/api/"

        //Map of RequestTypes to their respective url
        private val requests = mapOf(
            //These requests do not require authorization
            RequestType.LOGIN to "auth/login.php",
            RequestType.REGISTER to "auth/register.php",
            RequestType.UPDATE_PASS to "auth/update-forgot-password.php",
            RequestType.RESET_PASS_TOKEN to "auth/password-reset-token.php",
            RequestType.VALIDATE_TOKEN to "validate-token.php",

            //The following requests require authorization
            RequestType.GET_EVENTS to "user/event/events.php",
            RequestType.GET_CLIENTS to "user/client/clients.php",
            RequestType.GET_SERVICES to "user/service/services.php",
            RequestType.GET_RESOURCES to "user/resource/resources.php",
            RequestType.GET_SYSTEMS to "user/system/systems.php",
            RequestType.GET_VENUES to "user/venue/venues.php",
            RequestType.GET_STAFF to "user/staff/staff.php",
            RequestType.GET_SONGS to "user/song/songs.php",
            RequestType.GET_ANALYTICS to "dashboard/analytics/analytics.php",
            RequestType.GET_USER_INFO to "user/user/users.php"
        )

        //API request that calls the onComplete function with the returned JSONObject as its parameter, or onError if there is an error
        //Takes a RequestType and a JSONObject with any necessary parameters
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

        //Same formatting as previous function except also provides access token in request
        fun jsonRequestWithAuth(context: Context, type: RequestType, params: JSONObject, onComplete: (JSONObject) -> Unit,
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

        //Takes a VolleyError and returns the error jsonObject
        fun getErrorJSONObject(error: VolleyError) : JSONObject {
            return JSONObject(String(error.networkResponse.data)).getJSONObject("error")
        }
    }
}