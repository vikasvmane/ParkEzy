package com.whiterwalkers.parkezy.ui.utils

import android.content.Context
import android.content.SharedPreferences

object DataStore {

    lateinit var preferences: SharedPreferences
    fun setContext(context: Context) {
        preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    fun saveLoginStatus(isLoggedIn: Boolean) {
        preferences.edit().putBoolean(IS_SAVED, isLoggedIn).apply()
    }

    fun isLoggedIn() = preferences.getBoolean(IS_SAVED, false)
}