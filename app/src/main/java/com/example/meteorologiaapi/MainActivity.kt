package com.example.meteorologiaapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.meteorologiaapi.navigation.EntryPoint
import com.example.meteorologiaapi.viewModel.WeatherViewModel
import com.example.meteorologiaapi.ui.theme.MeteorologiaAPITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeteorologiaAPITheme {
                val navController = rememberNavController()
                val viewModel: WeatherViewModel = viewModel()
                EntryPoint(navController, viewModel)
            }
        }
    }
}