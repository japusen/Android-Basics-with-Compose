package com.example.movies.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.network.MoviesApi
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


class MoviesViewModel : ViewModel() {
    var moviesUiState: MoviesUiState by mutableStateOf(MoviesUiState.Loading)
        private set

    init {
        getMovies(Filter.POPULAR.value)
    }

    fun getMovies(filter: String) {
        viewModelScope.launch {
            moviesUiState = try {
                val movieList = MoviesApi.retrofitService.getMovies(filter).results
                MoviesUiState.Success("Success: ${movieList.size} movies retrieved")
            } catch (e: IOException) {
                MoviesUiState.Error
            }
        }
    }
}