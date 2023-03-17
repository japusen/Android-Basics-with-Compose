package com.example.movies.ui.screens

import com.example.movies.data.RequestType
import com.example.movies.model.Movie

data class MoviesUiState(
    val previousQuery: String = "",
    val query: String = "",
    val requestType: RequestType = RequestType.TOP_RATED,
    val currentSelectedMovie: Movie? = null,
    val isShowingSearchResults: Boolean = false,
    val isShowingMovieDetail: Boolean = false,
    val menuVisible: Boolean = false,
)