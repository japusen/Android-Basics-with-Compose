package com.example.movies.ui

import HomeScreen
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movies.R
import com.example.movies.data.RequestType
import com.example.movies.ui.screens.MoviesViewModel

@Composable
fun MoviesApp(
    moviesViewModel: MoviesViewModel,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { MovieTopAppBar(moviesViewModel) }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            color = MaterialTheme.colors.background
        ) {
            HomeScreen(repoRequestState = moviesViewModel.repoRequestState)
        }
    }
}

@Composable
fun MovieTopAppBar(
    moviesViewModel: MoviesViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by moviesViewModel.uiState.collectAsState()
    if (uiState.searchState)
        SearchBar(
            text = uiState.query,
            onTextChange = { moviesViewModel.updateQuery(it) },
            onArrowBackClicked = {
                moviesViewModel.updateQuery("")
                moviesViewModel.updateSearchState()
            },
            onSearchStarted = {
                moviesViewModel.updatePreviousQuery(uiState.query)
                moviesViewModel.updateRequestType(RequestType.SEARCH)
                moviesViewModel.updateSearchState()
                moviesViewModel.searchForMovie()
                moviesViewModel.updateQuery("")
            }
        )
    else
        TitleBar(
            query = uiState.previousQuery,
            requestType = uiState.requestType,
            onSearchClicked = { moviesViewModel.updateSearchState() }
        )
}

@Composable
fun TitleBar(
    query: String,
    requestType: RequestType,
    onSearchClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(
            text = when (requestType) {
                RequestType.TOP_RATED -> "Top Rated Movies"
                RequestType.POPULAR -> "Popular Movies"
                RequestType.SEARCH -> "Results for \"${query}\""
            },
            fontSize = 24.sp,
            style = MaterialTheme.typography.h1
        ) },
        backgroundColor = MaterialTheme.colors.primary,
//        navigationIcon = {
//            IconButton(onClick = {  }) {
//                Icon(Icons.Filled.Menu, contentDescription = null)
//            }
//        },
        actions = {
            SearchButton(
                onSearch = onSearchClicked
            )
        },
        elevation = 8.dp
    )
}

@Composable
fun SearchBar(
    text: String,
    onTextChange: (String) -> Unit,
    onArrowBackClicked: () -> Unit,
    onSearchStarted: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.primary
    ) {
        TextField(modifier = Modifier
            .fillMaxWidth(),
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    text = "Search for movies",
                    color = MaterialTheme.colors.onPrimary
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    onClick = { onArrowBackClicked() }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Go Back",
                        tint = Color.White
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        onTextChange("")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear Text",
                        tint = Color.White
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = { onSearchStarted() }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = Color.White.copy(alpha = ContentAlpha.medium)
            ))
    }
}


@Composable
fun SearchButton(
    onSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(onClick = { onSearch() }) {
        Icon(
            imageVector = Icons.Rounded.Search,
            contentDescription = stringResource(R.string.search),
        )
    }
}
