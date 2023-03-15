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

sealed interface RepoRequestState {
    data class Success(val movies: Flow<PagingData<Movie>>) : RepoRequestState
    object Error : RepoRequestState
    object Loading : RepoRequestState
}


class MoviesViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    var repoRequestState: RepoRequestState by mutableStateOf(RepoRequestState.Loading)
        private set


    init {
        loadTopRatedMovies()
    }

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

    fun searchForMovie(query: String) {
        repoRequestState = try {
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