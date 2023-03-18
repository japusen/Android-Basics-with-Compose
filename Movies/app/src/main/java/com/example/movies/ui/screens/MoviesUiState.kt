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
    val previousQuery: String = "",
    val query: String = "space",
    val selectedMovie: Movie? = null,
    val selectedTab: Int = TAB.TOP_RATED.num,
    val isShowingSearchResults: Boolean = false,
    val isShowingMovieDetail: Boolean = false,
    val menuVisible: Boolean = false,
)