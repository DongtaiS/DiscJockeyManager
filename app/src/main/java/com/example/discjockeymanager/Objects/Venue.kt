package com.example.discjockeymanager.Objects

import org.json.JSONObject
import java.io.Serializable

data class Venue(
    val userId: Int,
    val address: String,
    val city: String,
    val country: String,
    val name: String,
    val notes: String,
    val stateProvince: String,
    val zipcode: String
) : Serializable {
    companion object {
        fun parseVenue(v: JSONObject) : Venue {
            return Venue(
                v.getInt("user_id"),
                v.getString("venue_address"),
                v.getString("venue_city"),
                v.getString("venue_country"),
                v.getString("venue_name"),
                v.getString("venue_notes"),
                v.getString("venue_state_province"),
                v.getString("venue_zip"),
            )
        }
    }
}