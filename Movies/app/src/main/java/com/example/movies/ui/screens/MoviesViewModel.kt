package com.example.movies.ui.screens

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import java.io.IOException


class MoviesViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    lateinit var topRatedMoviesState: Flow<PagingData<Movie>>
    lateinit var popularMoviesState: Flow<PagingData<Movie>>

    var searchResultsState: Flow<PagingData<Movie>>? = null

    private val _uiState = MutableStateFlow(MoviesUiState())
    val uiState: StateFlow<MoviesUiState> = _uiState.asStateFlow()

    init {
        loadTopRatedMovies()
    }

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

    fun updateRequestType(newRequest: RequestType) {
        _uiState.update { currentState ->
            currentState.copy(
                requestType = newRequest
            )
        }
    }

    fun setMenuVisible(visible: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                menuVisible = visible
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

    fun setCurrentMovie(movie: Movie) {
        _uiState.update { currentState ->
            currentState.copy(
                currentSelectedMovie = movie
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

    fun loadTopRatedMovies() {
        topRatedMoviesState = moviesRepository.getTopRatedMovies().cachedIn(viewModelScope)
    }

    fun loadPopularMovies() {
        popularMoviesState = moviesRepository.getPopularMovies().cachedIn(viewModelScope)
    }

    fun searchForMovie() {
        val query = uiState.value.previousQuery
        searchResultsState = moviesRepository.movieSearch(query).cachedIn(viewModelScope)
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