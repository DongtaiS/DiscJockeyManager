package com.example.discjockeymanager.objects


import org.json.JSONObject
import java.io.Serializable
/**
 * Data class representing service, including function to parse from JSONObject
 */

data class DJService(
    val desc: String,
    val id: Int,
    val image: String,
    val price: Int,
    val name: String,
    val userId: Int
) : Serializable {
    companion object {
        fun parseService(s: JSONObject): DJService {
            return DJService(
                s.getString("description"),
                s.getInt("id"),
                s.getString("image"),
                s.getInt("price"),
                s.getString("service_name"),
                s.getInt("user_id")
                )
        }
    }
}