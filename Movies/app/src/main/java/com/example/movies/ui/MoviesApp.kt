package com.example.movies.ui

import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movies.model.Movie
import com.example.movies.ui.screens.HomeScreen
import com.example.movies.ui.screens.MoviesViewModel
import com.example.movies.ui.screens.TAB
import com.example.movies.ui.utils.ContentType
import com.example.movies.ui.utils.NavigationType


@Composable
fun MoviesApp(
    moviesViewModel: MoviesViewModel,
    windowSize: WindowWidthSizeClass,
) {

    val uiState = moviesViewModel.uiState.collectAsState().value

    val movieList = when (uiState.selectedTab) {
        TAB.TOP_RATED.num -> uiState.topRatedMovies?.collectAsLazyPagingItems()
        TAB.POPULAR.num -> uiState.popularMovies?.collectAsLazyPagingItems()
        else -> uiState.searchResults?.collectAsLazyPagingItems()
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
                    moviesViewModel.setIsShowingSearchTab(false)
                }
                TAB.POPULAR.num -> {
                    moviesViewModel.setCurrentMovie(null)
                    moviesViewModel.setIsShowingMovieDetail(false)
                    moviesViewModel.setIsShowingSearchTab(false)
                }
                TAB.SEARCH.num -> {
                    moviesViewModel.setCurrentMovie(null)
                    moviesViewModel.setIsShowingMovieDetail(false)
                    moviesViewModel.setIsShowingSearchTab(true)
                }
            }
        }
    }

    val onDetailScreenBackPressed = {
        moviesViewModel.setIsShowingMovieDetail(false)
    }

    val onSearchTextChange = { query: String ->
        moviesViewModel.updateQuery(query)
    }

    val onSearch = {
        moviesViewModel.searchForMovie()
        moviesViewModel.setCurrentMovie(null)
    }

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
        movieList = movieList,
        gridState = gridState,
        onMovieCardPressed = onMovieCardPressed,
        onDetailScreenBackPressed = onDetailScreenBackPressed,
        onTabPressed = onTabPressed,
        onSearchTextChange = onSearchTextChange,
        onSearch = onSearch
    )
}