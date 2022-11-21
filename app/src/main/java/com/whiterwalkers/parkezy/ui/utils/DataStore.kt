package com.whiterwalkers.parkezy.ui.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.whiterwalkers.parkezy.model.pojos.Car
import com.whiterwalkers.parkezy.model.pojos.Payment

object DataStore {
    private val gson = Gson()
    lateinit var preferences: SharedPreferences
    private val TAG = DataStore::class.java.simpleName
    fun setContext(context: Context) {
        preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    fun saveLoginStatus(isLoggedIn: Boolean) {
        preferences.edit().putBoolean(IS_SAVED, isLoggedIn).apply()
    }

    fun isLoggedIn() = preferences.getBoolean(IS_SAVED, false)

    fun saveSelectedCar(car: Car) {
        Log.d(TAG, "save selected car $car")
        preferences.edit().putString(USER_SELECTED_CAR, gson.toJson(car)).apply()
    }

    fun getSelectedCar(): Car? = if (!preferences.getString(USER_SELECTED_CAR, "")
            .isNullOrBlank()
    ) gson.fromJson(preferences.getString(USER_SELECTED_CAR, ""), Car::class.java) else null

    fun saveSelectedPayment(payment: Payment) {
        Log.d(TAG, "save selected payment $payment")
        preferences.edit().putString(USER_SELECTED_PAYMENT, gson.toJson(payment)).apply()
    }

    fun getSelectedPayment(): Payment? = if (!preferences.getString(USER_SELECTED_PAYMENT, "")
            .isNullOrBlank()
    ) gson.fromJson(preferences.getString(USER_SELECTED_PAYMENT, ""), Payment::class.java) else null
}