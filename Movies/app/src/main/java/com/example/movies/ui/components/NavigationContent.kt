package com.example.movies.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.movies.ui.screens.MoviesUiState
import com.example.movies.ui.screens.TAB

@Composable
fun MoviesBottomNavBar(
    uiState: MoviesUiState,
    navigationItemContentList: List<NavigationItemContent>,
) {
    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        for (navItem in navigationItemContentList) {
            NavigationBarItem(
                label = { Text(navItem.text) },
                selected = uiState.selectedTab == navItem.tab.num,
                onClick = { navItem.onTabPressed() },
                icon = { Icon(imageVector = navItem.icon, contentDescription = navItem.text) },
            )
        }
    }
}

@Composable
fun MoviesNavRail(
    uiState: MoviesUiState,
    navigationItemContentList: List<NavigationItemContent>,
) {
    NavigationRail(
        header = {
            Text(
                text = "Movies",
                style = MaterialTheme.typography.titleMedium,
            )
        }
    ) {

        for (navItem in navigationItemContentList) {
            NavigationRailItem(
                label = { Text(navItem.text) },
                selected = uiState.selectedTab == navItem.tab.num,
                onClick = { navItem.onTabPressed() },
                icon = { Icon(imageVector = navItem.icon, contentDescription = navItem.text) },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesNavDrawerContent(
    uiState: MoviesUiState,
    navigationItemContentList: List<NavigationItemContent>,
) {
    for (navItem in navigationItemContentList) {
        NavigationDrawerItem(
            label = { Text(navItem.text) },
            selected = uiState.selectedTab == navItem.tab.num,
            onClick = { navItem.onTabPressed() },
            icon = { Icon(imageVector = navItem.icon, contentDescription = navItem.text) },
        )
    }
}

data class NavigationItemContent(
    val text: String,
    val icon: ImageVector,
    val tab: TAB,
    val onTabPressed: () -> Unit
)