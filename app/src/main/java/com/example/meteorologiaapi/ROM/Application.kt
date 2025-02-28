package com.example.meteorologiaapi.ROM

import android.app.Application

class WeatherApplication : Application() {

    companion object {
        lateinit var database: WeatherDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = WeatherDatabase.getDatabase(this)
    }
}
