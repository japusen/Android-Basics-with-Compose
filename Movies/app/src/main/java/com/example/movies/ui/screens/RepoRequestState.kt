package com.example.movies.ui.screens

import androidx.paging.PagingData
import com.example.movies.model.Movie
import kotlinx.coroutines.flow.Flow

sealed interface RepoRequestState {
    data class Success(val movies: Flow<PagingData<Movie>>) : RepoRequestState
    object Error : RepoRequestState
    object Loading : RepoRequestState
}