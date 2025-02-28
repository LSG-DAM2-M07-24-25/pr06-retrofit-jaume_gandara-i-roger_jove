package com.example.meteorologiaapi.ROM

import com.example.meteorologiaapi.Model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository(private val weatherDao: WeatherDao) {

    suspend fun getAllWeatherResponses(): List<WeatherResponse> = withContext(Dispatchers.IO) {
        weatherDao.getAllWeatherResponses()
    }

    suspend fun getWeatherByCity(cityName: String): WeatherResponse? = withContext(Dispatchers.IO) {
        weatherDao.getWeatherByCity(cityName)
    }

    suspend fun insertWeatherResponse(weatherResponse: WeatherResponse) = withContext(Dispatchers.IO) {
        weatherDao.insertCoord(weatherResponse.coord)

        for (weather in weatherResponse.weather) {
            weatherDao.insertWeather(weather)
        }

        weatherDao.insertMain(weatherResponse.main)
        weatherDao.insertWind(weatherResponse.wind)
        weatherDao.insertClouds(weatherResponse.clouds)
        weatherDao.insertSys(weatherResponse.sys)

        weatherDao.insertWeatherResponse(weatherResponse)
    }

    suspend fun deleteWeatherResponse(weatherResponse: WeatherResponse) = withContext(Dispatchers.IO) {
        weatherDao.deleteWeatherResponse(weatherResponse)
    }
}
