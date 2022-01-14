package com.example.discjockeymanager

import com.example.discjockeymanager.Objects.User
import org.json.JSONObject

class LoggedInUser {
    companion object {
        var currentUser: User? = null
        fun parseJSON(userData: JSONObject): User {
            val ability = userData.getJSONArray("ability").getJSONObject(0)
            return User(
                userData.getInt("id"),
                userData.getString("fullName"),
                userData.getString("company"),
                userData.getString("role"),
                userData.getString("username"),
                userData.getString("country"),
                userData.getString("contact"),
                userData.getString("email"),
                userData.getString("currentPlan"),
                userData.getString("status"),
                userData.getString("avatar"),
                ability.getString("action"),
                ability.getString("subject")
            )
        }
    }
}