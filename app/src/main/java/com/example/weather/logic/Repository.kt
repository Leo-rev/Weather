package com.example.weather.logic

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.weather.logic.model.Place
import com.example.weather.logic.model.PlaceResponse
import com.example.weather.logic.model.Weather
import com.example.weather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext


object Repository {
    fun searchPlaces(query: String) = fire(Dispatchers.IO){
        val placeResponse=SunnyWeatherNetwork.searchPlaces(query)
        if (placeResponse.status == "ok") {
            val places = placeResponse.places
            Result.success(places)
        } else {
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }

    fun refreshWeather(lng:String,lat:String)= fire(Dispatchers.IO){
            coroutineScope {
                val deferreRealtime=async{
                    SunnyWeatherNetwork.getRealtimeWeather(lng,lat)
                }
                val deferredDaily=async {
                    SunnyWeatherNetwork.getDailyWeather(lng,lat)
                }
                val realtimeResponse=deferreRealtime.await()
                val dailyResponse=deferredDaily.await()
                if(realtimeResponse.status=="ok" && dailyResponse.status=="ok"){
                    val weather=Weather(realtimeResponse.result.realtime,
                                        dailyResponse.result.daily)
                    Result.success(weather)
                }else{
                    Result.failure(
                            RuntimeException(
                                    "realtime response status is ${realtimeResponse.status}"+
                                            "daily response status is  ${dailyResponse.status}"
                            )
                    )
                }
            }
    }

    private fun <T>fire(context:CoroutineContext,block:suspend() -> Result<T>)=
            liveData<Result<T>>(context){
                val result=try {
                    block()
                }catch (e:Exception){
                    Result.failure<T>(e)
                }
                emit(result)
            }
}