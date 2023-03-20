package com.example.movies.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun MoviesBottomNavigationBar(
    uiState: MoviesUiState,
    onTabPressed: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        NavigationBarItem(
            selected = uiState.selectedTab == TAB.TOP_RATED.num,
            onClick = { onTabPressed(TAB.TOP_RATED.num) },
            icon = { Icon(imageVector = Icons.Default.Star, contentDescription = "Top Rated") },
            label = { Text("Top Rated") }
        )
        NavigationBarItem(
            selected = uiState.selectedTab == TAB.POPULAR.num,
            onClick = { onTabPressed(TAB.POPULAR.num) },
            icon = { Icon(imageVector = Icons.Default.Favorite, contentDescription = "Popular") },
            label = { Text("Popular") }
        )
        NavigationBarItem(
            selected = uiState.selectedTab == TAB.SEARCH.num,
            onClick = { onTabPressed(TAB.SEARCH.num) },
            icon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Search") },
            label = { Text("Search") }
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
