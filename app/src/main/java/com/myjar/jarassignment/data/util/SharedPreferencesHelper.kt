package com.myjar.jarassignment.data.util

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesHelper(private val context: Context) {
    // SharedPreferences instance
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    fun saveString(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key: String, defaultValue: String = ""): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    companion object {
        const val API_DATA = "api_data"
    }
}

