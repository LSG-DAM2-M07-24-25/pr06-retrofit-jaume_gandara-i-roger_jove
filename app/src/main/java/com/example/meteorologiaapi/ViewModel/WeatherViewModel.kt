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

    private val _weatherDataList = MutableLiveData<List<WeatherResponse>>(emptyList())
    val weatherDataList: LiveData<List<WeatherResponse>> = _weatherDataList

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        // Cargar datos iniciales para múltiples ciudades
        loadInitialCities()
    }

    private fun loadInitialCities() {
        val cities = listOf("Madrid", "Barcelona")
        cities.forEach { city ->
            getWeather(city)
        }
    }

    fun getWeather(city: String) {
        viewModelScope.launch {
            try {
                val response: Response<WeatherResponse> = repository.getWeatherData(city, apiKey)
                if (response.isSuccessful) {
                    response.body()?.let { newWeather ->
                        // Actualizar la lista manteniendo las demás ciudades
                        val currentList = _weatherDataList.value ?: emptyList()
                        val updatedList = currentList.toMutableList()

                        // Encontrar y actualizar o añadir la nueva ciudad
                        val index = updatedList.indexOfFirst { it.name == city }
                        if (index != -1) {
                            updatedList[index] = newWeather
                        } else {
                            updatedList.add(newWeather)
                        }

                        _weatherDataList.value = updatedList
                    }
                } else {
                    _error.value = "Error: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Excepción: ${e.message}"
            }
        }
    }
}