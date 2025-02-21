package com.example.meteorologiaapi.Component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.meteorologiaapi.ViewModel.WeatherViewModel
import kotlin.math.roundToInt

@Composable
fun WeatherFavoriteContent(weatherViewModel: WeatherViewModel) {
    val favoriteCities by weatherViewModel.favoriteWeatherData.observeAsState(emptyList())
    val error by weatherViewModel.error.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, top = 96.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        favoriteCities.forEach { weather ->
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = weather.name,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${weather.main.temp.roundToInt()}°C",
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = weather.weather.firstOrNull()?.description ?: "",
                        fontSize = 24.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        WeatherDetail("Humidity", "${weather.main.humidity}%")
                        WeatherDetail("Wind", "${weather.wind.speed} m/s")
                        WeatherDetail("Pressure", "${weather.main.pressure} hPa")
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        WeatherDetail("Min", "${weather.main.temp_min.roundToInt()}°C")
                        WeatherDetail("Max", "${weather.main.temp_max.roundToInt()}°C")
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        error?.let { errorMessage ->
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}