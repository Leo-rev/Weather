package com.example.weather.logic.dao

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.weather.SunnyWeatherApplication
import com.example.weather.logic.model.Place
import com.example.weather.logic.network.SunnyWeatherNetwork
import com.google.gson.Gson

object PlaceDao {
    fun savePlace(place:Place){
        sharedPreferences().edit {
            putString("place",Gson().toJson(place))
        }
    }

    fun getSavedPlace():Place{
        val placeJson= sharedPreferences().getString("place","")
        return Gson().fromJson(placeJson,Place::class.java)
    }
    fun isPlaceSaved()= sharedPreferences().contains("place")
    private fun sharedPreferences()=SunnyWeatherApplication.context.
            getSharedPreferences("sunny_weather",0)
}