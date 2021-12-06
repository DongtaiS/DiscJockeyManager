package com.example.discjockeymanager

import android.content.Context
import android.content.SharedPreferences
import android.util.Log


class SharedPreferenceHelper {
    companion object {
        private const val SHARED_PREF = "Login"
        private const val PREF_USER = "Login_User"
        private const val PREF_ACCESS_TOKEN = "Login_Access_Token"
        private const val PREF_REFRESH_TOKEN = "Login_Refresh_Token"
        fun getSharedPreferences(ctx: Context?): SharedPreferences? {
            return ctx?.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        }
        fun setUserJSON(ctx: Context?, userName: String?) {
            val editor: SharedPreferences.Editor = getSharedPreferences(ctx)!!.edit()
            editor.putString(PREF_USER, userName)
            editor.apply()
        }

        fun getUserJSON(ctx: Context?): String? {
            return getSharedPreferences(ctx)!!.getString(PREF_USER, "")
        }
        fun setAuthTokens(ctx: Context?, accessToken: String, refreshToken: String) {
            Log.i("TESTTOKEN", "ASD")
            val editor: SharedPreferences.Editor = getSharedPreferences(ctx)!!.edit()
            editor.putString(PREF_ACCESS_TOKEN, accessToken)
            editor.putString(PREF_REFRESH_TOKEN, refreshToken)
            editor.apply()
        }
        fun getAccessToken(ctx: Context?): String? {
            return getSharedPreferences(ctx)!!.getString(PREF_ACCESS_TOKEN, "")
        }
        fun getRefreshToken(ctx: Context?): String? {
            return getSharedPreferences(ctx)!!.getString(PREF_REFRESH_TOKEN, "")
        }
    }
}