package com.example.meteorologiaapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.meteorologiaapi.Navigation.EntryPoint
import com.example.meteorologiaapi.ViewModel.WeatherViewModel
import com.example.meteorologiaapi.ui.theme.MeteorologiaAPITheme
    import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.meteorologiaapi.ui.theme.MeteorologiaAPITheme
import java.lang.reflect.Modifier

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeteorologiaAPITheme {
                val weatherViewModel: WeatherViewModel = viewModel()

                // Iniciar la consulta para el clima
                weatherViewModel.getWeather("London", "59e48412122472d2fd190a7305ea14a4")

                WeatherScreen(weatherViewModel)
            }
        }
    }
}

@Composable
fun WeatherScreen(weatherViewModel: WeatherViewModel) {
    val weatherData = weatherViewModel.weatherData.observeAsState()
    val errorData = weatherViewModel.error.observeAsState()

    Column {
        weatherData.value?.let {
            Text(text = "City: ${it.name}")
            Text(text = "Temperature: ${it.main.temp}°C")
            Text(text = "Description: ${it.weather[0].description}")
        }

        errorData.value?.let {
            Text(text = "Error: $it")
        }


        Button(onClick = {
            // Cambia la ciudad aquí
            weatherViewModel.getWeather("New York", "59e48412122472d2fd190a7305ea14a4")
        }) {
            Text("Get Weather")
        }
    }
}


