package com.example.movies.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material.BottomNavigation
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.movies.model.Movie
import com.example.movies.ui.utils.ContentType
import com.example.movies.ui.utils.NavigationType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigationType: NavigationType,
    contentType: ContentType,
    uiState: MoviesUiState,
    movieList: LazyPagingItems<Movie>?,
    gridState: LazyGridState,
    onMovieCardPressed: (Movie) -> Unit,
    onTabPressed: (Int) -> Unit,
    onDetailScreenBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {

    // Tablet
    if (navigationType == NavigationType.PERMANENT_NAVIGATION_DRAWER) {
        PermanentNavigationDrawer(
            drawerContent = {
                PermanentDrawerSheet(Modifier.width(175.dp)) {
                    MoviesNavDrawerContent(
                        uiState = uiState,
                        onTabPressed = onTabPressed,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        ) {
            AppContent(
                navigationType = navigationType,
                contentType = contentType,
                uiState = uiState,
                movieList = movieList,
                gridState = gridState,
                onMovieCardPressed = onMovieCardPressed,
                onTabPressed = onTabPressed
            )
        }

    } else {
        if (uiState.isShowingMovieDetail) {
            // Detail Screen
            if (uiState.selectedMovie != null) {
                MovieDetail(
                    movie = uiState.selectedMovie,
                    onBackPressed = onDetailScreenBackPressed,
                    isFullScreen = true,
                )
            }
        } else {
            // Grid Screen
            AppContent(
                navigationType = navigationType,
                contentType = contentType,
                uiState = uiState,
                movieList = movieList,
                gridState = gridState,
                onMovieCardPressed = onMovieCardPressed,
                onTabPressed = onTabPressed
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppContent(
    navigationType: NavigationType,
    contentType: ContentType,
    uiState: MoviesUiState,
    movieList: LazyPagingItems<Movie>?,
    gridState: LazyGridState,
    onMovieCardPressed: (Movie) -> Unit,
    onTabPressed: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxSize()) {
        AnimatedVisibility(visible = navigationType == NavigationType.NAVIGATION_RAIL) {
            // Navigation Content
            MoviesNavRail(
                uiState = uiState,
                onTabPressed = onTabPressed
            )
        }

        Scaffold(
            bottomBar = {
                if (navigationType == NavigationType.BOTTOM_NAVIGATION) { MoviesBottomNavigationBar(
                    uiState = uiState,
                    onTabPressed = onTabPressed
                ) }
            }
        ) {
            Column (
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.inverseOnSurface)
                    .padding(it)
            ) {

                if (contentType == ContentType.LIST_AND_DETAIL) {
                    MovieListAndDetailContent(
                        uiState = uiState,
                        movies = movieList,
                        gridState = gridState,
                        onMovieCardPressed = onMovieCardPressed
                    )
                } else {
                    MovieListOnlyContent(
                        movies = movieList,
                        gridState = gridState,
                        onMovieCardPressed = onMovieCardPressed
                    )
                }
            }
        }

    }
}