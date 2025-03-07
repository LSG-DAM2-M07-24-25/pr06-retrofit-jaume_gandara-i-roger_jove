package com.example.meteorologiaapi.Component

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.meteorologiaapi.ViewModel.WeatherViewModel
import kotlin.math.roundToInt

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun WeatherContent(weatherViewModel: WeatherViewModel) {
    val weatherDataList by weatherViewModel.weatherDataList.observeAsState(emptyList())
    val error by weatherViewModel.error.observeAsState()
    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Search Card
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                modifier = Modifier.fillMaxWidth()
                    .shadow(8.dp, shape = RoundedCornerShape(16.dp))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = MaterialTheme.colorScheme.primary
                    )

                    Box(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .fillMaxWidth()
                            .height(24.dp),
                    ) {
                        if (searchText.isEmpty()) {
                            Text(
                                text = "Buscar ciudad...",
                                fontSize = 18.sp,
                                color = Color.Gray
                            )
                        }

                        BasicTextField(
                            value = searchText,
                            onValueChange = { searchText = it },
                            textStyle = TextStyle(fontSize = 18.sp),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Button(
                            onClick = {
                                if (searchText.isNotEmpty()) {
                                    weatherViewModel.getWeather(searchText)
                                    searchText = ""
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier
                                .size(32.dp)
                                .align(Alignment.CenterEnd)  // Corregido aquí
                                .clip(RoundedCornerShape(8.dp))
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = Color.White,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Weather Cards
        weatherDataList.forEach { weather ->
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                        .shadow(12.dp, shape = RoundedCornerShape(16.dp))
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
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "${weather.main.temp.roundToInt()}°C",
                            fontSize = 48.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = weather.weather.firstOrNull()?.description ?: "",
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.secondary
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Column(
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row (
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                WeatherDetail("Humidity", "${weather.main.humidity}%")
                                WeatherDetail("Wind", "${weather.wind.speed} m/s")
                                WeatherDetail("Pressure", "${weather.main.pressure} hPa")
                            }
                            Row (
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                WeatherDetail("Min Temp", "${weather.main.temp_min.roundToInt()}°C")
                                WeatherDetail("Feels Like", "${weather.main.feels_like.roundToInt()}°C")
                                WeatherDetail("Max Temp", "${weather.main.temp_max.roundToInt()}°C")
                            }

                            Row (
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                WeatherDetail("Visibility", "${weather.visibility / 1000} km")
                                WeatherDetail("Sunrise", "${weather.sys.sunrise}")
                                WeatherDetail("Sunset", "${weather.sys.sunset}")
                            }
                        }
                    }

                    IconButton(
                        onClick = {
                            if (weatherViewModel.isCityInFavorites(weather.name)) {
                                weatherViewModel.removeFavoriteCity(weather.name)
                            } else {
                                weatherViewModel.addFavoriteCity(weather.name)
                            }
                        },
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Icon(
                            imageVector = if (weatherViewModel.isCityInFavorites(weather.name)) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Toggle Favorite",
                            tint = if (weatherViewModel.isCityInFavorites(weather.name)) Color.Red else Color.Gray
                        )
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
