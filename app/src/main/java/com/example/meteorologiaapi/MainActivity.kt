package com.example.meteorologiaapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.meteorologiaapi.Navigation.EntryPoint
import com.example.meteorologiaapi.ViewModel.WeatherViewModel
import com.example.meteorologiaapi.ui.theme.MeteorologiaAPITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeteorologiaAPITheme {
                val navController = rememberNavController()
                val weatherViewModel: WeatherViewModel = viewModel()
                EntryPoint(navController, weatherViewModel)
            }
        }
    }
}
