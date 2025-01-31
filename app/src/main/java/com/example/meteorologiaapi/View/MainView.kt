package com.example.meteorologiaapi.View

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.meteorologiaapi.ViewModel.WeatherViewModel

@Composable
fun MainView(navigationController: NavController, WeatherViewModel: WeatherViewModel) {
    Text("Main View")
}