package com.whiterwalkers.parkezy.di

import android.app.Application
import com.whiterwalkers.parkezy.ui.utils.DataStore
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ParkEzyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DataStore.setContext(applicationContext)
    }
}