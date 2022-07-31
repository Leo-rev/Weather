package com.example.weather.logic.network

import com.example.weather.SunnyWeatherApplication
import com.example.weather.logic.model.DailyResponse
import com.example.weather.logic.model.PlaceResponse
import com.example.weather.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {
    @GET("v2.5/${SunnyWeatherApplication.token}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@Path("lng") lng:String,@Path("lat") lat:String):
            Call<RealtimeResponse>
    @GET("v2.5/${SunnyWeatherApplication.token}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng") lng: String, @Path("lat") lat: String):
            Call<DailyResponse>
}