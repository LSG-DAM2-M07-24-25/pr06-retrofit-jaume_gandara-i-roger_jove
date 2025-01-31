package com.example.meteorologiaapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.meteorologiaapi.View.MainScreen
import com.example.meteorologiaapi.Navigation.EntryPoint
import com.example.meteorologiaapi.ui.theme.MeteorologiaAPITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeteorologiaAPITheme {
                EntryPoint()
            }
        }
    }
}
