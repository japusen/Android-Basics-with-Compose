package com.example.movies.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.movies.MoviesApplication
import com.example.movies.data.MoviesRepository
import kotlinx.coroutines.launch
import java.io.IOException

enum class Filter(val value: String) {
    TOP_RATED("top_rated"),
    POPULAR("popular")
}

sealed interface MoviesUiState {
    data class Success(val movies: String) : MoviesUiState
    object Error : MoviesUiState
    object Loading : MoviesUiState
}


class MoviesViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel() {
    var moviesUiState: MoviesUiState by mutableStateOf(MoviesUiState.Loading)
        private set

    init {
        getMovies(Filter.POPULAR.value)
    }

    fun getMovies(filter: String) {
        viewModelScope.launch {
            moviesUiState = try {
                val movieList = moviesRepository.getMovies(filter)
                MoviesUiState.Success("Success: ${movieList.size} movies retrieved")
            } catch (e: IOException) {
                MoviesUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MoviesApplication)
                val moviesRepository = application.container.moviesRepository
                MoviesViewModel(moviesRepository = moviesRepository)
            }
        }
    }
}