package com.example.discjockeymanager

import org.json.JSONObject
import java.io.Serializable

data class Staff(
    val address: String,
    val cellNumber: String,
    val city: String,
    val country: String,
    val email: String,
    val firstName: String,
    val gender: String,
    val id: Int,
    val lastName: String,
    val staffType: String,
    val stateProvince: String,
    val userId: Int,
    val zipcode: String
) : Serializable {
    companion object {
        fun parseStaff(s: JSONObject) : Staff {
            return Staff(
                s.getString("address"),
                s.getString("cell_number"),
                s.getString("city"),
                s.getString("country"),
                s.getString("email"),
                s.getString("first_name"),
                s.getString("gender"),
                s.getInt("id"),
                s.getString("last_name"),
                s.getString("staff_type"),
                s.getString("state_province"),
                s.getInt("user_id"),
                s.getString("zip")
            )
        }
    }
}