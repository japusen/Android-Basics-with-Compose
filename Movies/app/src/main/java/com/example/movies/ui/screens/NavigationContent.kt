package com.example.movies.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
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

@Composable
fun MovieTopAppBar(
    moviesViewModel: MoviesViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by moviesViewModel.uiState.collectAsState()
    if (uiState.isShowingSearchResults)
        SearchBar(
            text = uiState.query,
            onTextChange = { text: String ->
                moviesViewModel.updateQuery(text) },
            onArrowBackClicked = {
                moviesViewModel.updateQuery("")
                moviesViewModel.setIsShowingSearchResults()
            },
            onSearchStarted = {
                moviesViewModel.updatePreviousQuery(uiState.query)
                moviesViewModel.updateQuery("")
                moviesViewModel.updateRequestType(RequestType.SEARCH)
                moviesViewModel.setIsShowingSearchResults()
                moviesViewModel.searchForMovie()
            }
        )
    else
        TitleBar(
            query = uiState.previousQuery,
            requestType = uiState.requestType,
            onSearchClicked = { moviesViewModel.setIsShowingSearchResults() },
            menuVisible = uiState.menuVisible,
            onMenuClicked = { moviesViewModel.setMenuVisible(true) },
            onMenuDismissed = { moviesViewModel.setMenuVisible(false) },
            onTopRatedClicked = {
                if (uiState.requestType != RequestType.TOP_RATED) {
                    moviesViewModel.loadTopRatedMovies()
                    moviesViewModel.updateRequestType(RequestType.TOP_RATED)
                }
                moviesViewModel.setMenuVisible(false)
            },
            onPopularClicked = {
                if (uiState.requestType != RequestType.POPULAR) {
                    moviesViewModel.loadPopularMovies()
                    moviesViewModel.updateRequestType(RequestType.POPULAR)
                }
                moviesViewModel.setMenuVisible(false)
            }
        )
}

@Composable
fun TitleBar(
    query: String,
    requestType: RequestType,
    onSearchClicked: () -> Unit,
    menuVisible: Boolean,
    onMenuClicked: () -> Unit,
    onMenuDismissed: () -> Unit,
    onTopRatedClicked: () -> Unit,
    onPopularClicked: () -> Unit,
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
            IconButton(onClick = { onSearchClicked() }) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = stringResource(R.string.search),
                )
            }
            DropdownMenu(
                visible = menuVisible,
                onMenuClicked = onMenuClicked,
                onDismissed = onMenuDismissed,
                onTopRatedClicked = onTopRatedClicked,
                onPopularClicked= onPopularClicked,
            )
        },
        elevation = 8.dp
    )
}

@Composable
fun DropdownMenu(
    visible: Boolean,
    onMenuClicked: () -> Unit,
    onDismissed: () -> Unit,
    onTopRatedClicked: () -> Unit,
    onPopularClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Creating Icon button for dropdown menu
    IconButton(
        onClick = { onMenuClicked() }
    ) {
        Icon(Icons.Default.MoreVert, "")
    }

    // Creating a dropdown menu
    DropdownMenu(
        expanded = visible,
        onDismissRequest = { onDismissed() }
    ) {

        // Creating dropdown menu item, on click
        // would create a Toast message
        DropdownMenuItem(
            onClick = { onTopRatedClicked() }
        ) {
            Text(text = "Top Rated")
        }

        // Creating dropdown menu item, on click
        // would create a Toast message
        DropdownMenuItem(
            onClick = { onPopularClicked() }
        ) {
            Text(text = "Popular")
        }
    }
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
                    text = stringResource(R.string.search_hint),
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
                        tint = MaterialTheme.colors.onPrimary,
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
                        tint = MaterialTheme.colors.onPrimary
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