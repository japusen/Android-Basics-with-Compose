package com.example.movies.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.movies.model.Movie
import com.example.movies.ui.components.MovieDetail
import com.example.movies.ui.components.MovieGridScreen
import com.example.movies.ui.components.MoviesNavDrawerContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandedHomeScreen(
    uiState: MoviesUiState,
    movieList: LazyPagingItems<Movie>?,
    gridState: LazyGridState,
    onMovieCardPressed: (Movie) -> Unit,
    onTabPressed: (Int) -> Unit,
    onSearchTextChange: (String) -> Unit,
    onSearch: () -> Unit,
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
                MovieGridScreen(
                    uiState = uiState,
                    movieList = movieList,
                    gridState = gridState,
                    onMovieCardPressed = onMovieCardPressed,
                    onSearchTextChange = onSearchTextChange,
                    onSearch = onSearch,
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