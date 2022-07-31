package com.example.weather.logic.model

import com.google.gson.annotations.SerializedName


data class  Weather(val realtime:RealtimeResponse.Realtime,val daily:DailyResponse.Daily)