package com.example.movies.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.movies.MoviesApplication
import com.example.movies.data.MoviesRepository
import com.example.movies.data.RequestType
import com.example.movies.model.Movie
import kotlinx.coroutines.flow.*
import java.io.IOException


class MoviesViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    var movieListState: RepoRequestState by mutableStateOf(RepoRequestState.Loading)
        private set

    var searchResultsState: RepoRequestState by mutableStateOf(RepoRequestState.Loading)
        private set

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

    fun updateMenuVisible(visible: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                menuVisible = visible
            )
        }
    }

    fun updateIsShowingSearchResults() {
        _uiState.update { currentState ->
            currentState.copy(
                isShowingSearchResults = !uiState.value.isShowingSearchResults
            )
        }
    }

    fun updateCurrentMovie(movie: Movie) {
        _uiState.update { currentState ->
            currentState.copy(
                currentSelectedMovie = movie
            )
        }
    }

    fun updateIsShowingMovieDetail() {
        _uiState.update { currentState ->
            currentState.copy(
                isShowingMovieDetail = !uiState.value.isShowingMovieDetail
            )
        }
    }

    fun loadTopRatedMovies() {
        movieListState = try {
            val movieList = moviesRepository.getTopRatedMovies()
            RepoRequestState.Success(movieList)
        } catch (e: IOException) {
            RepoRequestState.Error
        }
    }

    fun loadPopularMovies() {
        movieListState = try {
            val movieList = moviesRepository.getPopularMovies()
            RepoRequestState.Success(movieList)
        } catch (e: IOException) {
            RepoRequestState.Error
        }
    }

    fun searchForMovie() {
        searchResultsState = try {
            val query = uiState.value.previousQuery
            val movieList = moviesRepository.movieSearch(query)
            RepoRequestState.Success(movieList)
        } catch (e: IOException) {
            RepoRequestState.Error
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