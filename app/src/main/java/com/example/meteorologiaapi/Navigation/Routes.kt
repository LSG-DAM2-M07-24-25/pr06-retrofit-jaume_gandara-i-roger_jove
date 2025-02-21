package com.example.meteorologiaapi.Navigation

sealed class Routes(val route: String) {
    object View1 : Routes("MainView") {
        fun createRoute() = "MainView";
    }
    object View2 : Routes("DetailsView") {
        fun createRoute() = "DetailsView";
    }
    object View3 : Routes("FavoritesView") {
        fun createRoute() = "FavoritesView";
    }
}