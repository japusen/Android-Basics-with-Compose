package com.example.movies.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.movies.model.Movie

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandedHomeScreen(
    uiState: MoviesUiState,
    movieList: LazyPagingItems<Movie>?,
    gridState: LazyGridState,
    onMovieCardPressed: (Movie) -> Unit,
    onTabPressed: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    PermanentNavigationDrawer(
        drawerContent = {
            PermanentDrawerSheet(modifier.width(175.dp)) {
                MoviesNavDrawerContent(
                    uiState = uiState,
                    onTabPressed = onTabPressed,
                    modifier = modifier.padding(8.dp)
                )
            }
        }
    ) {
        Surface(
            modifier = modifier.fillMaxSize()
        ) {
            Row {
                MovieGrid(
                    movies = movieList,
                    gridState = gridState,
                    onMovieCardPressed = onMovieCardPressed,
                    modifier = modifier
                        .weight(1f)
                )

                MovieDetail(
                    movie = uiState.selectedMovie,
                    modifier = modifier
                        .weight(1f)
                        .fillMaxSize()
                )
            }
        }
    }
}