package com.example.flightsearch.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearch.FlightSearchApplication
import com.example.flightsearch.data.Airport
import com.example.flightsearch.data.AirportDao
import com.example.flightsearch.data.Favorite
import com.example.flightsearch.data.FavoriteDao
import com.example.flightsearch.data.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FlightSearchViewModel(
    private val airportDao: AirportDao,
    private val favoriteDao: FavoriteDao,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val depart = mutableStateOf("")
    val query = mutableStateOf("")

    init {

    }

    fun getAirport(iataCode: String): Flow<Airport> = airportDao.getAirport(iataCode)
    fun getFlightsFrom(iataCode: String): Flow<List<Airport>> = airportDao.getFlights(iataCode)

    fun getAutoCompleteResults(query: String): Flow<List<Airport>> = airportDao.getAutoComplete(query)

    fun getFavorites(): Flow<List<Favorite>> = favoriteDao.getFavorites()

    suspend fun addFavorite(departureCode: String, destinationCode: String) {
        val favorite = Favorite(0, departureCode, destinationCode)
        favoriteDao.insert(favorite)
    }


    fun saveSearch(search: String) {
        viewModelScope.launch {
            userPreferencesRepository.saveSearch(search)
        }
    }



    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FlightSearchApplication)
                FlightSearchViewModel(
                    application.database.airportDao(),
                    application.database.favoriteDao(),
                    application.userPreferencesRepository
                )
            }
        }
    }
}