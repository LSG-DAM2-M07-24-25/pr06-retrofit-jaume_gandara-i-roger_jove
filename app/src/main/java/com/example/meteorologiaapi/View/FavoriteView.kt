package com.example.meteorologiaapi.View

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.meteorologiaapi.Component.Footer
import com.example.meteorologiaapi.Component.Header
import com.example.meteorologiaapi.Component.WeatherFavoriteContent
import com.example.meteorologiaapi.ViewModel.WeatherViewModel

@Composable
fun FavoriteView(navigationController: NavController, weatherViewModel: WeatherViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 56.dp)
                .verticalScroll(rememberScrollState())
        ) {
            WeatherFavoriteContent(weatherViewModel)
        }
        Header("Favorites")

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Footer(navigationController, 3)
        }
    }
}