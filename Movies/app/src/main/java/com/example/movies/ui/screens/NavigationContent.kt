package com.example.movies.ui.screens

import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesNavDrawerContent(
    uiState: MoviesUiState,
    onTabPressed: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationDrawerItem(
        icon = {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Top Rated"
            )
        },
        label = { Text("Top Rated") },
        selected = uiState.selectedTab == TAB.TOP_RATED.num,
        onClick = { onTabPressed(TAB.TOP_RATED.num) },
        modifier = modifier
    )
    NavigationDrawerItem(
        icon = {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Popular"
            )
        },
        label = { Text("Popular") },
        selected = uiState.selectedTab == TAB.POPULAR.num,
        onClick = { onTabPressed(TAB.POPULAR.num) },
        modifier = modifier
    )
    NavigationDrawerItem(
        icon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        },
        label = { Text("Search") },
        selected = uiState.selectedTab == TAB.SEARCH.num,
        onClick = { onTabPressed(TAB.SEARCH.num) },
        modifier = modifier
    )
}
