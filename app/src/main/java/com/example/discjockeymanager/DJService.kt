package com.example.discjockeymanager

import org.json.JSONObject
import java.io.Serializable

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