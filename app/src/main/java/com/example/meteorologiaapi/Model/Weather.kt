package com.example.meteorologiaapi.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_response")
data class WeatherResponse(
    @PrimaryKey val id: Int,
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Long,
    val sys: Sys,
    val timezone: Int,
    val name: String,
    val cod: Int
)

@Entity(tableName = "coord")
data class Coord(
    @PrimaryKey val id: Int,
    val lon: Double,
    val lat: Double
)

@Entity(tableName = "weather")
data class Weather(
    @PrimaryKey val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

@Entity(tableName = "main")
data class Main(
    @PrimaryKey val id: Int,
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int
)

@Entity(tableName = "wind")
data class Wind(
    @PrimaryKey val id: Int,
    val speed: Double,
    val deg: Int
)

@Entity(tableName = "clouds")
data class Clouds(
    @PrimaryKey val id: Int,
    val all: Int
)

@Entity(tableName = "sys")
data class Sys(
    @PrimaryKey val id: Int,
    val type: Int,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)
