package com.example.movies.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.paging.compose.LazyPagingItems
import com.example.movies.model.Movie
import com.example.movies.ui.screens.MoviesUiState

@Composable
fun GridAndDetail(
    uiState: MoviesUiState,
    movieList: LazyPagingItems<Movie>?,
    gridState: LazyGridState,
    onMovieCardPressed: (Movie) -> Unit,
    onSearchTextChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        Row {
            MovieGridScreen(
                isShowingSearchTab = uiState.isShowingSearchTab,
                query = uiState.query,
                movieList = movieList,
                gridState = gridState,
                onMovieCardPressed = onMovieCardPressed,
                onSearchTextChange = onSearchTextChange,
                onSearch = onSearch,
                modifier = modifier
                    .weight(1f)
                    .fillMaxHeight()
            )

            MovieDetail(
                movie = uiState.selectedMovie,
                modifier = modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.inverseOnSurface)
            )
        }
    }
}

@Composable
fun GridOnly(
    uiState: MoviesUiState,
    movieList: LazyPagingItems<Movie>?,
    gridState: LazyGridState,
    onMovieCardPressed: (Movie) -> Unit,
    onDetailScreenBackPressed: () -> Unit,
    onSearchTextChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier
) {

    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        AnimatedVisibility(
            visible = uiState.isShowingMovieDetail,
            enter = fadeIn() + slideInHorizontally { fullWidth -> -fullWidth/2 },
            exit = fadeOut() + slideOutHorizontally { fullWidth -> -fullWidth/2 },
        ) {
            BackHandler {
                onDetailScreenBackPressed()
            }
            // Detail Screen
            if (uiState.selectedMovie != null) {
                MovieDetail(
                    movie = uiState.selectedMovie,
                    onBackPressed = onDetailScreenBackPressed,
                    isFullScreen = true,
                )
            }
        }

        AnimatedVisibility(
            visible = !uiState.isShowingMovieDetail,
            enter = fadeIn(),
            exit = fadeOut()
        )  {
            MovieGridScreen(
                isShowingSearchTab = uiState.isShowingSearchTab,
                query = uiState.query,
                movieList = movieList,
                gridState = gridState,
                onMovieCardPressed = onMovieCardPressed,
                onSearchTextChange = onSearchTextChange,
                onSearch = onSearch,
            )
        }
    }
}