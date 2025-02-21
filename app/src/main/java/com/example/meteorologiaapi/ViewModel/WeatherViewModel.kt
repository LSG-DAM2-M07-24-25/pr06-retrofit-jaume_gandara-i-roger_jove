package com.example.meteorologiaapi.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meteorologiaapi.Model.WeatherResponse
import com.example.meteorologiaapi.WeatherRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class WeatherViewModel : ViewModel() {

    private val apiKey = "59e48412122472d2fd190a7305ea14a4"
    private val repository = WeatherRepository()

    private val _weatherData = MutableLiveData<WeatherResponse>()
    val weatherData: LiveData<WeatherResponse> = _weatherData

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getWeather(city: String) {
        viewModelScope.launch {
            try {
                val response: Response<WeatherResponse> = repository.getWeatherData(city, apiKey)
                if (response.isSuccessful) {
                    _weatherData.value = response.body()
                } else {
                    _error.value = "Error: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Excepción: ${e.message}"
            }
        }
    }
}