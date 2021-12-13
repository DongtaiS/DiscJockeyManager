package com.example.discjockeymanager

import org.json.JSONObject
import java.io.Serializable

data class DJSystem(
    val cableBox: String,
    val id: Int,
    val lights: String,
    val mixer: String,
    val players: String,
    val price: Int,
    val speakers: String,
    val systemName: String,
    val trussSystem: String,
    val userId: Int
) : Serializable {
    companion object {
        fun parseSystem(s: JSONObject): DJSystem {
            return DJSystem(
                s.getString("cable_box"),
                s.getInt("id"),
                s.getString("lights"),
                s.getString("mixer"),
                s.getString("players"),
                s.getInt("price"),
                s.getString("speakers"),
                s.getString("system_name"),
                s.getString("truss_system"),
                s.getInt("user_id")
            )
        }
    }
}