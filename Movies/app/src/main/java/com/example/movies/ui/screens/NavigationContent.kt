package com.example.movies.ui.screens

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun MoviesTabRow (
    uiState: MoviesUiState,
    onTabPressed: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    TabRow(
        selectedTabIndex = uiState.selectedTab,
    ) {
        Tab(
            selected = uiState.selectedTab == TAB.TOP_RATED.num,
            onClick = { onTabPressed(TAB.TOP_RATED.num) },
            text = {
                Text(
                    text = "Top Rated",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        )
        Tab(
            selected = uiState.selectedTab == TAB.POPULAR.num,
            onClick = { onTabPressed(TAB.POPULAR.num) },
            text = {
                Text(
                    text = "Popular",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        )
        Tab(
            selected = uiState.selectedTab == TAB.SEARCH.num,
            onClick = { onTabPressed(TAB.SEARCH.num) },
            text = {
                Text(
                    text = "Search",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        )
    }
}

@Composable
fun MoviesNavRail(
    uiState: MoviesUiState,
    onTabPressed: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationRail {
        NavigationRailItem(
            selected = uiState.selectedTab == TAB.TOP_RATED.num,
            onClick = { onTabPressed(TAB.TOP_RATED.num) },
            icon = { Icon(imageVector = Icons.Default.Star, contentDescription = "Top Rated") },
            label = { Text("Top Rated") }
        )
        NavigationRailItem(
            selected = uiState.selectedTab == TAB.POPULAR.num,
            onClick = { onTabPressed(TAB.POPULAR.num) },
            icon = { Icon(imageVector = Icons.Default.Favorite, contentDescription = "Popular") },
            label = { Text("Popular") }
        )
        NavigationRailItem(
            selected = uiState.selectedTab == TAB.SEARCH.num,
            onClick = { onTabPressed(TAB.SEARCH.num) },
            icon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Search") },
            label = { Text("Search") }
        )
    }
}