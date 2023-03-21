package com.example.movies.ui

import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movies.model.Movie
import com.example.movies.ui.screens.*


@Composable
fun MoviesApp(
    moviesViewModel: MoviesViewModel,
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {

    val uiState = moviesViewModel.uiState.collectAsState().value

    val movieList = when(uiState.selectedTab) {
        TAB.TOP_RATED.num ->
            uiState.topRatedMovies?.collectAsLazyPagingItems()
        TAB.POPULAR.num ->
            uiState.popularMovies?.collectAsLazyPagingItems()
        else ->
            uiState.searchResults?.collectAsLazyPagingItems()
    }

    val gridState = rememberLazyGridState()

    val onMovieCardPressed = { movie: Movie ->
        moviesViewModel.setCurrentMovie(movie)
        moviesViewModel.setIsShowingMovieDetail(true)
    }

    val onTabPressed = { num: Int ->
        if (num != uiState.selectedTab) {
            moviesViewModel.setSelectedTab(num)
            when (num) {
                TAB.TOP_RATED.num -> {
                    moviesViewModel.setCurrentMovie(null)
                    moviesViewModel.setIsShowingMovieDetail(false)
                }
                TAB.POPULAR.num -> {
                    moviesViewModel.setCurrentMovie(null)
                    moviesViewModel.setIsShowingMovieDetail(false)
                }
                TAB.SEARCH.num -> {
                    moviesViewModel.setCurrentMovie(null)
                    moviesViewModel.setIsShowingMovieDetail(false)
                }
            }
        }
    }

    val onDetailScreenBackPressed = {
        moviesViewModel.setIsShowingMovieDetail(false)
    }

    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            CompactHomeScreen(
                uiState = uiState,
                movieList = movieList,
                gridState = gridState,
                onMovieCardPressed = onMovieCardPressed,
                onDetailScreenBackPressed = onDetailScreenBackPressed,
                onTabPressed = onTabPressed,
                modifier = modifier
            )
        }
        WindowWidthSizeClass.Medium -> {
            MediumHomeScreen(
                uiState = uiState,
                movieList = movieList,
                gridState = gridState,
                onMovieCardPressed = onMovieCardPressed,
                onDetailScreenBackPressed = onDetailScreenBackPressed,
                onTabPressed = onTabPressed,
                modifier = modifier
            )
        }
        WindowWidthSizeClass.Expanded -> {
            ExpandedHomeScreen(
                uiState = uiState,
                movieList = movieList,
                gridState = gridState,
                onMovieCardPressed = onMovieCardPressed,
                onTabPressed = onTabPressed,
                modifier = modifier
            )
        }
    }
}
