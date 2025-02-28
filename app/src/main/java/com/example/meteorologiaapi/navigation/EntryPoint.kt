package com.example.meteorologiaapi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.meteorologiaapi.view.MainView
import com.example.meteorologiaapi.view.DetailsView
import com.example.meteorologiaapi.view.FavoriteView
import com.example.meteorologiaapi.viewModel.WeatherViewModel

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
