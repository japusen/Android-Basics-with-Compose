package com.example.movies.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.movies.data.RequestType

data class MoviesUiState(
    val previousQuery: String = "",
    val query: String = "",
    val requestType: RequestType = RequestType.TOP_RATED,
    val searchState: Boolean = false
)