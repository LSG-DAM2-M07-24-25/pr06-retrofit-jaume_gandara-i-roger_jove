package com.example.meteorologiaapi.Component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
            .padding(top = 96.dp, start = 16.dp, end = 16.dp, bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        favoriteCities.forEach { weather ->
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    modifier = Modifier.fillMaxWidth(1f)
                ) {
                    BoxWithConstraints {
                        if (this.maxWidth > 412.dp) {
                            // Horizontal layout for wide screens
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                // Left column with main information
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = weather.name,
                                        fontSize = 28.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "${weather.main.temp.roundToInt()}°C",
                                        fontSize = 48.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = weather.weather.firstOrNull()?.description ?: "",
                                        fontSize = 24.sp
                                    )
                                }

                                // Right column with details
                                Column(modifier = Modifier.weight(1f)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceEvenly
                                    ) {
                                        WeatherDetail("Humidity", "${weather.main.humidity}%")
                                        WeatherDetail("Wind", "${weather.wind.speed} m/s")
                                    }

                                    Spacer(modifier = Modifier.height(16.dp))
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
                        } else {
                            // Vertical layout for narrow screens
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
                                }

                                Spacer(modifier = Modifier.height(16.dp))
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
                    }
                }
            }
        }

        // Error message
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