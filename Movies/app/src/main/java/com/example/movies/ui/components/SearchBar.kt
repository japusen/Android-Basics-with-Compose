package com.example.movies.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.movies.ui.screens.MoviesUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    uiState: MoviesUiState,
    onSearchTextChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier
) {

    var searchHasFocus by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Surface(
        tonalElevation = 8.dp,
        shape = RoundedCornerShape(50.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, bottom = 16.dp)
    ) {
        if (uiState.isShowingSearchTab) {
            TextField(
                value = uiState.query,
                onValueChange = { onSearchTextChange(it) },
                placeholder = {
                    Text(text = "Search for movies")
                },
                leadingIcon = {
                    if (searchHasFocus) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back Arror",
                            modifier = Modifier.clickable {
                                searchHasFocus = false
                                onSearchTextChange("")
                                focusManager.clearFocus()
                            }
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Bar"
                        )
                    }
                },
                trailingIcon = {
                    if (searchHasFocus) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear",
                            modifier = Modifier.clickable {
                                onSearchTextChange("")
                            }
                        )
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Gray,
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearch()
                        onSearchTextChange("")
                        focusManager.clearFocus()
                    }
                ),
                modifier = modifier
                    .focusable()
                    .onFocusChanged { searchHasFocus = it.hasFocus }
            )
        }
    }
}