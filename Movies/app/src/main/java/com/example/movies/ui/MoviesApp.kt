package com.example.movies.ui

import HomeScreen
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movies.R
import com.example.movies.ui.screens.MoviesViewModel

@Composable
fun MoviesApp(
    moviesViewModel: MoviesViewModel,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { MovieTopAppBar() }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            color = MaterialTheme.colors.background
        ) {
            HomeScreen(moviesUiState = moviesViewModel.moviesUiState)
        }
    }
}

@Composable
fun MovieTopAppBar(
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(
            text = stringResource(R.string.app_name),
            fontSize = 24.sp,
            style = MaterialTheme.typography.h1
        ) },
        backgroundColor = MaterialTheme.colors.primary,
        navigationIcon = {
            IconButton(onClick = { /* TODO */ }) {
                Icon(Icons.Filled.Menu, contentDescription = null)
            }
        },
        actions = { SearchButton({ /* TODO */ })},
        elevation = 8.dp
    )
}

@Composable
fun SearchButton(
    onSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(onClick = { onSearch }) {
        Icon(
            imageVector = Icons.Rounded.Search,
            contentDescription = stringResource(R.string.search),
        )
    }
}
