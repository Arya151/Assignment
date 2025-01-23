package com.myjar.jarassignment

import android.app.Application
import com.myjar.jarassignment.data.util.SharedPreferencesHelper


class App : Application() {

    companion object {
        lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    }

    override fun onCreate() {
        super.onCreate()
        sharedPreferencesHelper = SharedPreferencesHelper(applicationContext)
    }
}