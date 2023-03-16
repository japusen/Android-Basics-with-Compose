package com.example.movies.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.paging.PagingData
import com.example.movies.MoviesApplication
import com.example.movies.data.MoviesRepository
import com.example.movies.data.RequestType
import com.example.movies.model.Movie
import kotlinx.coroutines.flow.*
import java.io.IOException


class MoviesViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    var repoRequestState: RepoRequestState by mutableStateOf(RepoRequestState.Loading)
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

    fun updateSearchState() {
        _uiState.update { currentState ->
            currentState.copy(
                searchState = !uiState.value.searchState
            )
        }    }

    fun loadTopRatedMovies() {
        repoRequestState = try {
            val movieList = moviesRepository.getTopRatedMovies()
            RepoRequestState.Success(movieList)
        } catch (e: IOException) {
            RepoRequestState.Error
        }
    }

    fun loadPopularMovies() {
        repoRequestState = try {
            val movieList = moviesRepository.getPopularMovies()
            RepoRequestState.Success(movieList)
        } catch (e: IOException) {
            RepoRequestState.Error
        }
    }

    fun searchForMovie() {
        repoRequestState = try {
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