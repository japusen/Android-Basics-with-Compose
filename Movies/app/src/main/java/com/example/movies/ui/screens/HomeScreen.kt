package com.example.movies.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.movies.model.Movie
import com.example.movies.ui.components.*
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
    onDetailScreenBackPressed: () -> Unit,
    onTabPressed: (Int) -> Unit,
    onSearchTextChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    val navigationItemContentList = listOf<NavigationItemContent>(NavigationItemContent(
        text = "Top Rated",
        icon = Icons.Default.Star,
        tab = TAB.TOP_RATED,
        onTabPressed = { onTabPressed(TAB.TOP_RATED.num) },
    ), NavigationItemContent(text = "Popular",
        icon = Icons.Default.Favorite,
        tab = TAB.POPULAR,
        onTabPressed = { onTabPressed(TAB.POPULAR.num) }), NavigationItemContent(text = "Search",
        icon = Icons.Default.Search,
        tab = TAB.SEARCH,
        onTabPressed = { onTabPressed(TAB.SEARCH.num) }))

    if (navigationType == NavigationType.PERMANENT_NAVIGATION_DRAWER) {
        PermanentNavigationDrawer(drawerContent = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Movies",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                PermanentDrawerSheet(modifier.width(175.dp)) {
                    MoviesNavDrawerContent(
                        uiState = uiState,
                        navigationItemContentList = navigationItemContentList,
                    )
                }
            }
        }) {
            AppContent(
                navigationType = navigationType,
                contentType = contentType,
                uiState = uiState,
                movieList = movieList,
                gridState = gridState,
                onMovieCardPressed = onMovieCardPressed,
                onDetailScreenBackPressed = onDetailScreenBackPressed,
                onSearchTextChange = onSearchTextChange,
                onSearch = onSearch,
                navigationItemContentList = navigationItemContentList,
            )
        }
    } else {
        AppContent(
            navigationType = navigationType,
            contentType = contentType,
            uiState = uiState,
            movieList = movieList,
            gridState = gridState,
            onMovieCardPressed = onMovieCardPressed,
            onDetailScreenBackPressed = onDetailScreenBackPressed,
            onSearchTextChange = onSearchTextChange,
            onSearch = onSearch,
            navigationItemContentList = navigationItemContentList,
        )
    }
}

/**
 * Composable that displays content at home screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppContent(
    navigationType: NavigationType,
    contentType: ContentType,
    uiState: MoviesUiState,
    movieList: LazyPagingItems<Movie>?,
    gridState: LazyGridState,
    onMovieCardPressed: (Movie) -> Unit,
    onDetailScreenBackPressed: () -> Unit,
    onSearchTextChange: (String) -> Unit,
    onSearch: () -> Unit,
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxSize()) {
        AnimatedVisibility(visible = navigationType == NavigationType.NAVIGATION_RAIL) {
            MoviesNavRail(
                uiState = uiState, navigationItemContentList = navigationItemContentList
            )
        }
        Scaffold(
            bottomBar = {
                AnimatedVisibility(visible = navigationType == NavigationType.BOTTOM_NAVIGATION) {
                    MoviesBottomNavBar(
                        uiState = uiState, navigationItemContentList = navigationItemContentList
                    )
                }
            },
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inverseOnSurface)
        ) { padding ->
            if (contentType == ContentType.LIST_AND_DETAIL) {
                GridAndDetail(
                    uiState = uiState,
                    movieList = movieList,
                    gridState = gridState,
                    onMovieCardPressed = onMovieCardPressed,
                    onSearchTextChange = onSearchTextChange,
                    onSearch = onSearch
                )
            } else {
                GridOnly(
                    uiState = uiState,
                    movieList = movieList,
                    gridState = gridState,
                    onMovieCardPressed = onMovieCardPressed,
                    onDetailScreenBackPressed = onDetailScreenBackPressed,
                    onSearchTextChange = onSearchTextChange,
                    onSearch = onSearch,
                    bottomPadding = if (navigationType == NavigationType.BOTTOM_NAVIGATION) padding.calculateBottomPadding()
                    else 0.dp
                )
            }
        }
    }
}