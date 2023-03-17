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
    modifier: Modifier = Modifier
) {
    TabRow(
        selectedTabIndex = 0,
    ) {
        Tab(
            selected = true,
            onClick = { },
            text = {
                Text(
                    text = "Top Rated",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        )
        Tab(
            selected = false,
            onClick = { },
            text = {
                Text(
                    text = "Popular",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        )
        Tab(
            selected = false,
            onClick = { },
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
    modifier: Modifier = Modifier
) {
    NavigationRail {
        NavigationRailItem(
            selected = true,
            onClick = {  },
            icon = { Icon(imageVector = Icons.Default.Star, contentDescription = "Top Rated") },
            label = { Text("Top Rated") }
        )
        NavigationRailItem(
            selected = false,
            onClick = {  },
            icon = { Icon(imageVector = Icons.Default.Favorite, contentDescription = "Popular") },
            label = { Text("Popular") }
        )
        NavigationRailItem(
            selected = false,
            onClick = {  },
            icon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Search") },
            label = { Text("Search") }
        )
    }
}