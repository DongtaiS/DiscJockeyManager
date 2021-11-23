package com.example.discjockeymanager

import android.content.Context
import android.content.SharedPreferences


class SharedPreferenceHelper {
    companion object {
        private const val SHARED_PREF = "Login"
        private const val PREF_USER = "Login_User"
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
    }
}