package com.example.movies.ui.screens

import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movies.MoviesApplication
import com.example.movies.data.MoviesRepository
import com.example.movies.data.RequestType
import com.example.movies.model.Movie
import kotlinx.coroutines.flow.*


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

    fun updatePreviousQuery(query: String) {
        _uiState.update { currentState ->
            currentState.copy(
                previousQuery = query.trimStart().trimEnd().toLowerCase(Locale.current)
            )
        }
    }

    fun updateQuery(newQuery: String) {
        _uiState.update { currentState ->
            currentState.copy(
                query = newQuery
            )
        }
    }

    fun setIsShowingSearchResults() {
        _uiState.update { currentState ->
            currentState.copy(
                isShowingSearchResults = !uiState.value.isShowingSearchResults
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

    fun loadTopRatedMovies() {
        _uiState.update { currentState ->
            currentState.copy(
                topRatedMovies = moviesRepository.getTopRatedMovies().cachedIn(viewModelScope)
            )
        }
        return
    }

    fun loadPopularMovies() {
        _uiState.update { currentState ->
            currentState.copy(
                popularMovies = moviesRepository.getPopularMovies().cachedIn(viewModelScope)
            )
        }
    }

    fun searchForMovie() {
        val query = uiState.value.previousQuery
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