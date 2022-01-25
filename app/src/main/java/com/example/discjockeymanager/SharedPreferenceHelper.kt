package com.example.discjockeymanager

import android.content.Context
import android.content.SharedPreferences
/**
 * Helper to store simple login data using SharedPreferences system
 */


class SharedPreferenceHelper {
    companion object {
        private const val SHARED_PREF = "Login"
        private const val PREF_USER = "Login_User"
        private const val PREF_ACCESS_TOKEN = "Login_Access_Token"
        private const val PREF_REFRESH_TOKEN = "Login_Refresh_Token"

        //Gets reference to SharedPreferences object from context
        private fun getSharedPreferences(ctx: Context?): SharedPreferences? {
            return ctx?.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        }

        //Stores raw user information json as string
        fun setUserJSON(ctx: Context?, userName: String?) {
            val editor: SharedPreferences.Editor = getSharedPreferences(ctx)!!.edit()
            editor.putString(PREF_USER, userName)
            editor.apply()
        }

        //Returns raw user information json as string
        fun getUserJSON(ctx: Context?): String? {
            return getSharedPreferences(ctx)!!.getString(PREF_USER, "")
        }

        //Stores access and refresh tokens for authorization
        fun setAuthTokens(ctx: Context?, accessToken: String, refreshToken: String) {
            val editor: SharedPreferences.Editor = getSharedPreferences(ctx)!!.edit()
            editor.putString(PREF_ACCESS_TOKEN, accessToken)
            editor.putString(PREF_REFRESH_TOKEN, refreshToken)
            editor.apply()
        }

        //Returns access token for authorization
        fun getAccessToken(ctx: Context?): String? {
            return getSharedPreferences(ctx)!!.getString(PREF_ACCESS_TOKEN, "")
        }

        //Returns refresh token to generate new access token without logging in
        fun getRefreshToken(ctx: Context?): String? {
            return getSharedPreferences(ctx)!!.getString(PREF_REFRESH_TOKEN, "")
        }
    }
}