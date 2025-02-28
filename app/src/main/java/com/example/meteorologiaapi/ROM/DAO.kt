package com.example.meteorologiaapi.ROM

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.meteorologiaapi.Model.*

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather_response")
    fun getAllWeatherResponses(): List<WeatherResponse>

    @Query("SELECT * FROM weather_response WHERE name = :cityName")
    fun getWeatherByCity(cityName: String): WeatherResponse?

    @Insert
    fun insertWeatherResponse(weatherResponse: WeatherResponse)

    @Delete
    fun deleteWeatherResponse(weatherResponse: WeatherResponse)

    @Query("SELECT * FROM coord WHERE id = :coordId")
    fun getCoordById(coordId: Int): Coord

    @Insert
    fun insertCoord(coord: Coord)

    @Query("SELECT * FROM weather WHERE id = :weatherId")
    fun getWeatherById(weatherId: Int): Weather

    @Insert
    fun insertWeather(weather: Weather)

    @Query("SELECT * FROM main WHERE id = :mainId")
    fun getMainById(mainId: Int): Main

    @Insert
    fun insertMain(main: Main)

    @Query("SELECT * FROM wind WHERE id = :windId")
    fun getWindById(windId: Int): Wind

    @Insert
    fun insertWind(wind: Wind)

    @Query("SELECT * FROM clouds WHERE id = :cloudsId")
    fun getCloudsById(cloudsId: Int): Clouds

    @Insert
    fun insertClouds(clouds: Clouds)

    @Query("SELECT * FROM sys WHERE id = :sysId")
    fun getSysById(sysId: Int): Sys

    @Insert
    fun insertSys(sys: Sys)
}