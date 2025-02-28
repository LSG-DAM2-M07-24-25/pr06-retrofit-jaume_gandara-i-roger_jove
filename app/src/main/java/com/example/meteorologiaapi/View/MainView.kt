package com.example.meteorologiaapi.View

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.meteorologiaapi.ViewModel.WeatherViewModel
import com.example.meteorologiaapi.Component.Header
import com.example.meteorologiaapi.Component.Footer
import com.example.meteorologiaapi.Component.WeatherContent
import com.example.meteorologiaapi.Component.WeatherFavoriteContent

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun MainView(navigationController: NavController, weatherViewModel: WeatherViewModel) {
    var showContent by remember { mutableStateOf(true) }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 56.dp)
        ) {

            // Content Area
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                when (showContent) {
                    true -> {
                        Column {
                            Header("WeatherApp")
                            WeatherContent(weatherViewModel)
                        }
                    }
                    false -> {
                        Column {
                            Header("Favorites")
                            WeatherFavoriteContent(weatherViewModel)
                        }
                    }
                }
            }
        }

        // Footer always visible at bottom
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Footer(navigationController, 1)
        }
    }
}