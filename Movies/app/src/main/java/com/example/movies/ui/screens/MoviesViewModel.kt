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
import androidx.paging.PagingData
import com.example.movies.MoviesApplication
import com.example.movies.data.MoviesRepository
import com.example.movies.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface MoviesUiState {
    data class Success(val movies: Flow<PagingData<Movie>>) : MoviesUiState
    object Error : MoviesUiState
    object Loading : MoviesUiState
}


class MoviesViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    var moviesUiState: MoviesUiState by mutableStateOf(MoviesUiState.Loading)
        private set


    init {
        loadPopularMovies()
    }

    fun loadTopRatedMovies() {
        viewModelScope.launch {
            moviesUiState = try {
                val movieList = moviesRepository.getTopRatedMovies()
                MoviesUiState.Success(movieList)
            } catch (e: IOException) {
                MoviesUiState.Error
            }
        }
    }

    fun loadPopularMovies() {
        viewModelScope.launch {
            moviesUiState = try {
                val movieList = moviesRepository.getPopularMovies()
                MoviesUiState.Success(movieList)
            } catch (e: IOException) {
                MoviesUiState.Error
            }
        }
    }

    fun searchForMovie(query: String) {
        viewModelScope.launch {
            moviesUiState = try {
                val movieList = moviesRepository.movieSearch(query)
                MoviesUiState.Success(movieList)
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