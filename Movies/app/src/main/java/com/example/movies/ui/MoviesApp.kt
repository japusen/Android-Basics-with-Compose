package com.example.movies.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.movies.model.Movie
import com.example.movies.ui.screens.HomeScreen
import com.example.movies.ui.screens.MoviesViewModel
import com.example.movies.ui.utils.ContentType
import com.example.movies.ui.utils.NavigationType

@Composable
fun MoviesApp(
    moviesViewModel: MoviesViewModel,
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {

    val uiState = moviesViewModel.uiState.collectAsState().value
    val movieListState = moviesViewModel.movieListState
    val searchResultsState = moviesViewModel.searchResultsState

    val navigationType: NavigationType
    val contentType: ContentType

    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            navigationType = NavigationType.BOTTOM_NAVIGATION
            contentType = ContentType.LIST_ONLY
        }
        WindowWidthSizeClass.Medium -> {
            navigationType = NavigationType.NAVIGATION_RAIL
            contentType = ContentType.LIST_ONLY
        }
        WindowWidthSizeClass.Expanded -> {
            navigationType = NavigationType.PERMANENT_NAVIGATION_DRAWER
            contentType = ContentType.LIST_AND_DETAIL
        }
        else -> {
            navigationType = NavigationType.BOTTOM_NAVIGATION
            contentType = ContentType.LIST_ONLY
        }
    }

    HomeScreen(
        navigationType = navigationType,
        contentType = contentType,
        uiState = uiState,
        movieListState = movieListState,
        searchResultsState = searchResultsState,
        onMovieCardPressed = { movie: Movie ->
            moviesViewModel.setCurrentMovie(movie)
            moviesViewModel.setIsShowingMovieDetail(true)
        },
        onDetailScreenBackPressed = { moviesViewModel.setIsShowingMovieDetail(false) },
        modifier = modifier
    )
}
