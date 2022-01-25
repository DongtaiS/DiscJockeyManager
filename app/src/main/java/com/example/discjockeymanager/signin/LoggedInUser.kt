package com.example.discjockeymanager.signin


import com.example.discjockeymanager.objects.User
import org.json.JSONObject
/**
 * Helper class to parse User object from JSONObject and
 * manage current user
 */

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