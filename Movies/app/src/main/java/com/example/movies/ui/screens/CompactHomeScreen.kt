package com.example.movies.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.example.movies.model.Movie
import com.example.movies.ui.components.MovieDetail
import com.example.movies.ui.components.MovieGridScreen
import com.example.movies.ui.components.MoviesBottomNavigationBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompactHomeScreen(
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

    Scaffold(
        bottomBar = {
            MoviesBottomNavigationBar(
                uiState = uiState,
                onTabPressed = onTabPressed
            )
        },
        modifier = modifier.fillMaxSize()
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(bottom = padding.calculateBottomPadding())
        ) {
            if (uiState.isShowingMovieDetail) {
                BackHandler {
                    onDetailScreenBackPressed()
                }
                // Detail Screen
                if (uiState.selectedMovie != null) {
                    MovieDetail(
                        movie = uiState.selectedMovie,
                        onBackPressed = onDetailScreenBackPressed,
                        isFullScreen = true,
                        modifier = modifier.fillMaxSize()
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