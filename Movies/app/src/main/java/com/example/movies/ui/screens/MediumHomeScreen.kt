package com.example.movies.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.example.movies.model.Movie

@Composable
fun MediumHomeScreen(
    uiState: MoviesUiState,
    movieList: LazyPagingItems<Movie>?,
    gridState: LazyGridState,
    onMovieCardPressed: (Movie) -> Unit,
    onDetailScreenBackPressed: () -> Unit,
    onTabPressed: (Int) -> Unit,
    onSearchTextChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier.fillMaxSize()
    ) {
        MoviesNavRail(
            uiState = uiState,
            onTabPressed = onTabPressed,
        )

        Surface(
            modifier = modifier.fillMaxSize()
        ) {
            if (uiState.isShowingMovieDetail) {
                BackHandler() {
                    onDetailScreenBackPressed()
                }
                // Detail Screen
                if (uiState.selectedMovie != null) {
                    MovieDetail(
                        movie = uiState.selectedMovie,
                        onBackPressed = onDetailScreenBackPressed,
                        isFullScreen = true
                    )
                }
            } else {
                MovieGridScreen(
                    uiState = uiState,
                    movieList = movieList,
                    gridState = gridState,
                    onMovieCardPressed = onMovieCardPressed,
                    onSearchTextChange = onSearchTextChange,
                    onSearch = onSearch
                )
            }
        }
    }
}