package com.example.meteorologiaapi.Navigation

sealed class Routes(val route: String) {
    object View1 : Routes("MainView")
    object View2 : Routes("DetailsView")
    object View3 : Routes("FavoritesView")
}