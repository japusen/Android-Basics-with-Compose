package com.example.movies.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.paging.cachedIn
import com.example.movies.MoviesApplication
import com.example.movies.data.MoviesRepository
import com.example.movies.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class MoviesViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        MoviesUiState()
    )

    init {
        loadPopularMovies()
        loadTopRatedMovies()
    }

    val uiState: StateFlow<MoviesUiState> = _uiState.asStateFlow()

    fun updateQuery(newQuery: String) {
        _uiState.update { currentState ->
            currentState.copy(
                query = newQuery
            )
        }
    }

    fun setIsShowingSearchTab(bool: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                isShowingSearchTab = bool
            )
        }
    }

    fun setCurrentMovie(movie: Movie?) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedMovie = movie
            )
        }
    }

    fun setIsShowingMovieDetail(bool: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                isShowingMovieDetail = bool
            )
        }
    }

    fun setSelectedTab(num: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedTab = num
            )
        }
    }

    private fun loadTopRatedMovies() {
        _uiState.update { currentState ->
            currentState.copy(
                topRatedMovies = moviesRepository.getTopRatedMovies().cachedIn(viewModelScope)
            )
        }
        return
    }

    private fun loadPopularMovies() {
        _uiState.update { currentState ->
            currentState.copy(
                popularMovies = moviesRepository.getPopularMovies().cachedIn(viewModelScope)
            )
        }
    }

    fun searchForMovie() {
        val query = uiState.value.query
        _uiState.update { currentState ->
            currentState.copy(
                searchResults = moviesRepository.movieSearch(query).cachedIn(viewModelScope)
            )
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