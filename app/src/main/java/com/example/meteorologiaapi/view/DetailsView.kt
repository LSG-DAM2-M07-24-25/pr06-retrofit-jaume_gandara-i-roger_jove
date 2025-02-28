package com.example.meteorologiaapi.view

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.meteorologiaapi.viewModel.WeatherViewModel

@Composable
fun DetailsView(navigationController: NavController, WeatherViewModel: WeatherViewModel) {
    Text("Details View")
}