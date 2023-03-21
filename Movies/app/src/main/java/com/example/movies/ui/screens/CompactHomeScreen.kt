package com.example.movies.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.example.movies.model.Movie


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompactHomeScreen(
    uiState: MoviesUiState,
    movieList: LazyPagingItems<Movie>?,
    gridState: LazyGridState,
    onMovieCardPressed: (Movie) -> Unit,
    onDetailScreenBackPressed: () -> Unit,
    onTabPressed: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    Scaffold(
        bottomBar = {
            MoviesBottomNavigationBar(
                uiState = uiState,
                onTabPressed = onTabPressed
            )
        }
    ) {
        if (uiState.isShowingMovieDetail) {
            BackHandler {
                onDetailScreenBackPressed()
            }
            // Detail Screen
            if (uiState.selectedMovie != null) {
                MovieDetail(
                    movie = uiState.selectedMovie,
                    // onBackPressed = onDetailScreenBackPressed,
                    isFullScreen = true,
                    modifier = modifier.fillMaxSize()
                )
            }
        } else {
            MovieGrid(
                movies = movieList,
                gridState = gridState,
                onMovieCardPressed = onMovieCardPressed,
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.inverseOnSurface)
                    .padding(it)
            )
        }
    }
}