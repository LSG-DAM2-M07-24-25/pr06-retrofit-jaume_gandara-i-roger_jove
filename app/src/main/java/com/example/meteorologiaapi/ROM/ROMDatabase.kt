package com.example.meteorologiaapi.ROM

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.meteorologiaapi.Model.Clouds
import com.example.meteorologiaapi.Model.Coord
import com.example.meteorologiaapi.Model.Main
import com.example.meteorologiaapi.Model.Sys
import com.example.meteorologiaapi.Model.Weather
import com.example.meteorologiaapi.Model.WeatherResponse
import com.example.meteorologiaapi.Model.Wind
import androidx.room.TypeConverters

@Database(
    entities = [
        WeatherResponse::class,
        Coord::class,
        Weather::class,
        Main::class,
        Wind::class,
        Clouds::class,
        Sys::class
    ],
    version = 1
)
@TypeConverters(WeatherTypeConverters::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao

    companion object {
        @Volatile
        private var INSTANCE: WeatherDatabase? = null

        fun getDatabase(context: Context): WeatherDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDatabase::class.java,
                    "weather_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
