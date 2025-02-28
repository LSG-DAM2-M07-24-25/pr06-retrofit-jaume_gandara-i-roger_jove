package com.example.meteorologiaapi.rom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.meteorologiaapi.model.Clouds
import com.example.meteorologiaapi.model.Coord
import com.example.meteorologiaapi.model.Main
import com.example.meteorologiaapi.model.Sys
import com.example.meteorologiaapi.model.Weather
import com.example.meteorologiaapi.model.WeatherResponse
import com.example.meteorologiaapi.model.Wind
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
