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

    // We'll add Room functionality without changing the existing structure

    private val _weatherDataList = MutableLiveData<List<WeatherResponse>>(emptyList())
    val weatherDataList: LiveData<List<WeatherResponse>> = _weatherDataList

    private val _favoriteWeatherData = MutableLiveData<List<WeatherResponse>>(emptyList())
    val favoriteWeatherData: LiveData<List<WeatherResponse>> = _favoriteWeatherData

    private val _favoriteCities = MutableLiveData<List<String>>(emptyList()) // Llista de ciutats preferides
    val favoriteCities: LiveData<List<String>> = _favoriteCities

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        loadInitialCities()
    }

    private fun loadInitialCities() {
        val cities = listOf("Barcelona")
        val favoriteCities = listOf("Lleida", "Girona", "Tarragona", "Barcelona")
        cities.forEach { city -> getWeather(city) }
        favoriteCities.forEach { city -> addFavoriteCity(city) }
    }

    /**
     * Obté la informació meteorològica d'una ciutat i actualitza la llista **sense sobrescriure altres ciutats**.
     */
    fun getWeather(city: String) {
        viewModelScope.launch {
            try {
                val response: Response<WeatherResponse> = repository.getWeatherData(city, apiKey)
                if (response.isSuccessful) {
                    response.body()?.let { newWeather ->
                        _weatherDataList.value = listOf(newWeather)
                    }
                } else {
                    _error.value = "Error: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Excepció: ${e.message}"
            }
        }
    }

    /**
     * Obté la informació meteorològica per a les ciutats preferides i **les afegeix sense sobrescriure**.
     */
    fun getFavoriteWeather(city: String) {
        viewModelScope.launch {
            try {
                val response: Response<WeatherResponse> = repository.getWeatherData(city, apiKey)
                if (response.isSuccessful) {
                    response.body()?.let { newWeather ->
                        val currentList = _favoriteWeatherData.value ?: emptyList()
                        val updatedList = currentList.toMutableList()

                        // Si la ciutat ja existeix, l'actualitzem; si no, l'afegim
                        val index = updatedList.indexOfFirst { it.name == city }
                        if (index != -1) {
                            updatedList[index] = newWeather
                        } else {
                            updatedList.add(newWeather)
                        }

                        _favoriteWeatherData.value = updatedList

                        // Here we could save to Room DB if properly set up
                    }
                } else {
                    _error.value = "Error: ${response.message()} per a $city"
                }
            } catch (e: Exception) {
                _error.value = "Excepció: ${e.message}"
            }
        }
    }

    /**
     * Obté el temps per a totes les ciutats preferides (de manera individual).
     */
    fun refreshFavoriteWeather() {
        val favoriteCitiesList = _favoriteCities.value ?: emptyList()
        favoriteCitiesList.forEach { city -> getFavoriteWeather(city) }
    }

    /**
     * Afegeix una ciutat a la llista de favorits i refresca la seva informació meteorològica.
     */
    fun addFavoriteCity(city: String) {
        val currentFavorites = _favoriteCities.value?.toMutableList() ?: mutableListOf()
        if (!currentFavorites.contains(city)) {
            currentFavorites.add(city)
            _favoriteCities.value = currentFavorites
        }
        getFavoriteWeather(city) // Actualitza només aquesta ciutat
    }

    /**
     * Elimina una ciutat de la llista de favorits.
     */
    fun removeFavoriteCity(city: String) {
        val currentFavorites = _favoriteCities.value?.toMutableList() ?: mutableListOf()
        if (currentFavorites.contains(city)) {
            currentFavorites.remove(city)
            _favoriteCities.value = currentFavorites

            val updatedList = _favoriteWeatherData.value?.filterNot { it.name == city } ?: emptyList()
            _favoriteWeatherData.value = updatedList
        }
    }
}