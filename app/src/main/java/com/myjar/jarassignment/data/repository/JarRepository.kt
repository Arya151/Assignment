package com.myjar.jarassignment.data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.myjar.jarassignment.App
import com.myjar.jarassignment.data.api.ApiService
import com.myjar.jarassignment.data.model.ComputerItem
import com.myjar.jarassignment.data.util.SharedPreferencesHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface JarRepository {
    suspend fun fetchResults(): Flow<List<ComputerItem>>
}

class JarRepositoryImpl(
    private val apiService: ApiService
) : JarRepository {
    override suspend fun fetchResults(): Flow<List<ComputerItem>> = flow {
        var data = emptyList<ComputerItem>()

        val cachedData = App.sharedPreferencesHelper.getString(SharedPreferencesHelper.API_DATA, "")
        val type = object : TypeToken<List<ComputerItem>>() {}.type
        if(cachedData.isNotEmpty()) data = Gson().fromJson(cachedData, type)
        if (data.isEmpty()) {
            data = apiService.fetchResults()
            App.sharedPreferencesHelper.saveString(SharedPreferencesHelper.API_DATA,Gson().toJson(data))
        }
        emit(data)
    }
}