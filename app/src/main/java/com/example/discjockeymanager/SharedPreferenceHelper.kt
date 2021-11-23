package com.example.discjockeymanager

import android.content.Context
import android.preference.PreferenceManager

import android.content.SharedPreferences
import android.util.Log


class SharedPreferenceHelper {
    companion object {
        val SHARED_PREF = "Login"
        val PREF_USERNAME = "Login_User"
        fun getSharedPreferences(ctx: Context?): SharedPreferences? {
            return ctx?.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        }
        fun setUserName(ctx: Context?, userName: String?) {
            val editor: SharedPreferences.Editor = getSharedPreferences(ctx)!!.edit()
            editor.putString(PREF_USERNAME, userName)
            editor.apply()
        }

        fun getUserName(ctx: Context?): String? {
            return getSharedPreferences(ctx)!!.getString(PREF_USERNAME, "")
        }
    }
}