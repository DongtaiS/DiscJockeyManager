package com.example.discjockeymanager

import android.os.Build
import androidx.annotation.RequiresApi
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class Client (
    val address: String,
    val cellNumber: String,
    val city: String,
    val companyName: String,
    val company_url: String,
    val country: String,
    val email: String,
    val facebookUrl: String,
    val firstName: String,
    val id: Int,
    val instagramUrl: String,
    val lastName: String,
    val password: String,
    val regionId: String,
    val state: String,
    val twitterUrl: String,
    val userId: Int,
    val username: String,
    val weddingMicrositeUrl: String,
    val zipcode: String,
        ) : Serializable {
            companion object {
                fun parseClient(c: JSONObject): Client {
                    return Client(
                        c.getString("address"),
                        c.getString("cell_number"),
                        c.getString("city"),
                        c.getString("company_name"),
                        c.getString("company_url"),
                        c.getString("country"),
                        c.getString("email"),
                        c.getString("facebook_url"),
                        c.getString("first_name"),
                        c.getInt("id"),
                        c.getString("instagram_url"),
                        c.getString("last_name"),
                        c.getString("password"),
                        c.getString("region_id"),
                        c.getString("state"),
                        c.getString("twitter_url"),
                        c.getInt("user_id"),
                        c.getString("username"),
                        c.getString("wedding_microsite_url"),
                        c.getString("zip")
                    )
                }
            }
}