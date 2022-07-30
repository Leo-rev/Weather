package com.example.weather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class SunnyWeatherApplication: Application() {
    companion object{
        const val token="PTsvkVOhNjbwh0zc"
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context=applicationContext
    }
}