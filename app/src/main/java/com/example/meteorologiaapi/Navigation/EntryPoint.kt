package com.example.meteorologiaapi.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.meteorologiaapi.View.MainView
import com.example.meteorologiaapi.View.DetailsView
import com.example.meteorologiaapi.View.FavoriteView
import com.example.meteorologiaapi.ViewModel.WeatherViewModel

@Composable
fun EntryPoint(navController: NavHostController, viewModel: WeatherViewModel) {
    val startDestination = Routes.View1.route

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Routes.View1.route) {
            MainView(navController, viewModel)
        }
        composable(Routes.View2.route) {
            DetailsView(navController, viewModel)
        }
        composable(Routes.View3.route) {
            FavoriteView(navController, viewModel)
        }
    }
}
