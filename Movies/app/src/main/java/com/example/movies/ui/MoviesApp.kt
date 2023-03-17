package com.example.movies.ui

import android.util.Log
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movies.data.RequestType
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

    val movieList = if (uiState.requestType == RequestType.TOP_RATED)
                        moviesViewModel.topRatedMoviesState.collectAsLazyPagingItems()
                    else
                        moviesViewModel.popularMoviesState.collectAsLazyPagingItems()

    val searchResults = moviesViewModel.searchResultsState?.collectAsLazyPagingItems()


    val navigationType: NavigationType
    val contentType: ContentType

    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            navigationType = NavigationType.BOTTOM_NAVIGATION
            contentType = ContentType.LIST_ONLY
            Log.d("WindowWidth", "COMPACT")
        }
        WindowWidthSizeClass.Medium -> {
            navigationType = NavigationType.NAVIGATION_RAIL
            contentType = ContentType.LIST_ONLY
            Log.d("WindowWidth", "MEDIUM")
        }
        WindowWidthSizeClass.Expanded -> {
            navigationType = NavigationType.PERMANENT_NAVIGATION_DRAWER
            contentType = ContentType.LIST_AND_DETAIL
            Log.d("WindowWidth", "EXPANDED")
        }
        else -> {
            navigationType = NavigationType.BOTTOM_NAVIGATION
            contentType = ContentType.LIST_AND_DETAIL
            Log.d("WindowWidth", "OTHER")
        }
    }

    HomeScreen(
        navigationType = navigationType,
        contentType = contentType,
        uiState = uiState,
        movieList = movieList,
        gridState = rememberLazyGridState(),
        searchResults = searchResults,
        onMovieCardPressed = { movie: Movie ->
            moviesViewModel.setCurrentMovie(movie)
            moviesViewModel.setIsShowingMovieDetail(true)
        },
        onDetailScreenBackPressed = { moviesViewModel.setIsShowingMovieDetail(false) },
        modifier = modifier
    )
}
