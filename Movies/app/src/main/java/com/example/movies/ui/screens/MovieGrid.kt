package com.example.movies.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movies.R
import com.example.movies.model.Movie

// Configuration: https://developers.themoviedb.org/3/configuration/get-api-configuration
private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"
private const val IMAGE_SIZE = "w780"

@Composable
fun MovieGridScreen(
    uiState: MoviesUiState,
    movieList: LazyPagingItems<Movie>?,
    gridState: LazyGridState,
    onMovieCardPressed: (Movie) -> Unit,
    onSearchTextChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier
) {

    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = modifier
    ) {
        MovieGrid(
            movies = movieList,
            gridState = gridState,
            onMovieCardPressed = onMovieCardPressed,
            modifier = modifier
                .background(androidx.compose.material3.MaterialTheme.colorScheme.inverseOnSurface)
        )

        SearchBar(
            uiState = uiState,
            onSearchTextChange = onSearchTextChange,
            onSearch = onSearch
        )
    }
}
@Composable
fun MovieGrid(
    movies: LazyPagingItems<Movie>?,
    gridState: LazyGridState,
    onMovieCardPressed: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        state = gridState,
        columns = GridCells.Adaptive(150.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(6.dp)
    ) {
        if (movies != null) {
            items(movies.itemCount) { index ->
                MovieCard(
                    movie = movies[index]!!,
                    onMovieCardPressed = onMovieCardPressed
                )
            }
        }
    }
}

@Composable
fun MovieCard(
    movie: Movie,
    onMovieCardPressed: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = 16.dp,
        modifier = modifier
            .clickable { onMovieCardPressed(movie) }
    ) {
        if (movie.posterPath != null) {
            MoviePoster(movie)
        } else {
            MovieNoPoster(movie, modifier)
        }
    }
}

@Composable
fun MoviePoster(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(256.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("$IMAGE_BASE_URL$IMAGE_SIZE${movie.posterPath}")
                .crossfade(true)
                .build(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            contentDescription = movie.title,
            contentScale = ContentScale.FillBounds,
        )
    }
}

@Composable
fun MovieNoPoster(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(Color.Black)
            .height(256.dp)
    ) {
        Text(
            text = if (movie.releaseDate.isNotEmpty())
                "${movie.title} (${movie.releaseDate.slice(0..3)})"
            else
                movie.title,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h5,
            color = Color.White
        )
    }
}