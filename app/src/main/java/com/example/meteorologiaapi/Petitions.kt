package com.example.meteorologiaapi

import com.example.meteorologiaapi.Model.WeatherResponse
import retrofit2.Response

class WeatherRepository {
    private val api = RetrofitInstance.weatherApi

    suspend fun getWeatherData(city: String, apiKey: String): Response<WeatherResponse> {
        return api.getWeather(city, apiKey)
    }
}
