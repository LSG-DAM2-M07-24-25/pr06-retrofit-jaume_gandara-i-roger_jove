package com.example.meteorologiaapi.rom

import androidx.room.TypeConverter
import com.example.meteorologiaapi.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WeatherTypeConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromWeatherList(value: List<Weather>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toWeatherList(value: String): List<Weather> {
        val listType = object : TypeToken<List<Weather>>() {}.type
        return gson.fromJson(value, listType)
    }
}
