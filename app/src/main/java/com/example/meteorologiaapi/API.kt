package com.example.meteorologiaapi

import com.example.meteorologiaapi.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("q") city: String, // Ciudad
        @Query("appid") apiKey: String, // Tu clave API
        @Query("units") units: String = "metric" // Opcional: puedes elegir entre "metric" o "imperial"
    ): Response<WeatherResponse>

}
