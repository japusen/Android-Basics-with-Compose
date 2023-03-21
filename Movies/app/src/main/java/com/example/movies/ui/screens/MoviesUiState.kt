package com.example.movies.ui.screens

import androidx.paging.PagingData
import com.example.movies.model.Movie
import kotlinx.coroutines.flow.Flow

enum class TAB(val num: Int) {
    TOP_RATED(0),
    POPULAR(1),
    SEARCH(2)
}

data class MoviesUiState(
    val topRatedMovies: Flow<PagingData<Movie>>? = null,
    val popularMovies: Flow<PagingData<Movie>>? = null,
    val searchResults: Flow<PagingData<Movie>>? = null,
    val query: String = "",
    val selectedMovie: Movie? = null,
    val selectedTab: Int = TAB.TOP_RATED.num,
    val isShowingSearchTab: Boolean = false,
    val isShowingMovieDetail: Boolean = false,
)