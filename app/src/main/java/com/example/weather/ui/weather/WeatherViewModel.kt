package com.example.weather.ui.weather

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.weather.logic.Repository
import com.example.weather.logic.model.PlaceResponse

class WeatherViewModel: ViewModel() {
    private val locationLiveData = MutableLiveData<com.example.weather.logic.model.Location>()
    var locationLng = ""
    var locationLat = ""
    var placeName = ""
    val weatherLiveData = Transformations.switchMap(locationLiveData) { location ->
        Repository.refreshWeather(location.lng, location.lat)
    }

    fun refreshWeather(lng: String, lat: String) {
        locationLiveData.value = com.example.weather.logic.model.Location(lng, lat)
    }

}