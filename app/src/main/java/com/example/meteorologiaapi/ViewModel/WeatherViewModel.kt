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

    // LiveData for weather data
    private val _weatherDataList = MutableLiveData<List<WeatherResponse>>(emptyList())
    val weatherDataList: LiveData<List<WeatherResponse>> = _weatherDataList

    private val _favoriteWeatherData = MutableLiveData<List<WeatherResponse>>(emptyList())
    val favoriteWeatherData: LiveData<List<WeatherResponse>> = _favoriteWeatherData

    private val _favoriteCities = MutableLiveData<List<String>>(emptyList())
    val favoriteCities: LiveData<List<String>> = _favoriteCities

    // Loading state
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    // Error handling
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    // Currently selected city
    private val _selectedCity = MutableLiveData<String>()
    val selectedCity: LiveData<String> = _selectedCity

    init {
        loadInitialCities()
    }

    private fun loadInitialCities() {
        val cities = listOf("Barcelona")
        val favoriteCities = listOf("Lleida", "Girona", "Tarragona", "Barcelona")
        cities.forEach { city -> getWeather(city) }
        _favoriteCities.value = favoriteCities
        refreshFavoriteWeather()
    }

    /**
     * Gets weather information for a city and updates the list.
     */
    fun getWeather(city: String) {
        _isLoading.value = true
        _error.value = null
        viewModelScope.launch {
            try {
                val response: Response<WeatherResponse> = repository.getWeatherData(city, apiKey)
                if (response.isSuccessful) {
                    response.body()?.let { newWeather ->
                        _weatherDataList.value = listOf(newWeather)
                        _selectedCity.value = city
                    }
                } else {
                    _error.value = "Error: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Exception: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Gets weather information for favorite cities and adds them without overwriting.
     */
    fun getFavoriteWeather(city: String) {
        viewModelScope.launch {
            try {
                val response: Response<WeatherResponse> = repository.getWeatherData(city, apiKey)
                if (response.isSuccessful) {
                    response.body()?.let { newWeather ->
                        val currentList = _favoriteWeatherData.value ?: emptyList()
                        val updatedList = currentList.toMutableList()

                        // If the city already exists, update it; otherwise, add it
                        val index = updatedList.indexOfFirst { it.name.equals(city, ignoreCase = true) }
                        if (index != -1) {
                            updatedList[index] = newWeather
                        } else {
                            updatedList.add(newWeather)
                        }

                        _favoriteWeatherData.value = updatedList
                    }
                } else {
                    _error.value = "Error: ${response.message()} for $city"
                }
            } catch (e: Exception) {
                _error.value = "Exception: ${e.message}"
            }
        }
    }

    /**
     * Gets weather for all favorite cities (individually).
     */
    fun refreshFavoriteWeather() {
        _isLoading.value = true
        _error.value = null
        val favoriteCitiesList = _favoriteCities.value ?: emptyList()

        if (favoriteCitiesList.isEmpty()) {
            _isLoading.value = false
            return
        }

        viewModelScope.launch {
            try {
                favoriteCitiesList.forEach { city ->
                    getFavoriteWeather(city)
                }
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Adds a city to the favorites list and refreshes its weather information.
     */
    fun addFavoriteCity(city: String) {
        val currentFavorites = _favoriteCities.value?.toMutableList() ?: mutableListOf()
        if (!currentFavorites.contains(city)) {
            currentFavorites.add(city)
            _favoriteCities.value = currentFavorites
            getFavoriteWeather(city)
        }
    }

    /**
     * Removes a city from the favorites list.
     */
    fun removeFavoriteCity(city: String) {
        val currentFavorites = _favoriteCities.value?.toMutableList() ?: mutableListOf()
        if (currentFavorites.contains(city)) {
            currentFavorites.remove(city)
            _favoriteCities.value = currentFavorites

            val updatedList = _favoriteWeatherData.value?.filterNot {
                it.name.equals(city, ignoreCase = true)
            } ?: emptyList()
            _favoriteWeatherData.value = updatedList
        }
    }

    /**
     * Clears any error messages
     */
    fun clearError() {
        _error.value = null
    }

    /**
     * Checks if a city is in favorites
     */
    fun isCityInFavorites(city: String): Boolean {
        return _favoriteCities.value?.any { it.equals(city, ignoreCase = true) } ?: false
    }
}