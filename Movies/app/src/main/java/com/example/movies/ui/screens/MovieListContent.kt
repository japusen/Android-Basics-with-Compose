package com.example.movies.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movies.R
import com.example.movies.model.Movie

// Configuration: https://developers.themoviedb.org/3/configuration/get-api-configuration
private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"
private const val IMAGE_SIZE = "w780"

@Composable
fun MovieListOnlyContent(
    movieState: RepoRequestState,
    onMovieCardPressed: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    when (movieState) {
        is RepoRequestState.Loading -> LoadingScreen(modifier)
        is RepoRequestState.Success -> MovieGrid(
            movies = movieState.movies.collectAsLazyPagingItems(),
            onMovieCardPressed = onMovieCardPressed
        )
        is RepoRequestState.Error -> ErrorScreen(modifier)
    }
}

@Composable
fun MovieListAndDetailContent(
    uiState: MoviesUiState,
    movieState: RepoRequestState,
    onMovieCardPressed: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        when (movieState) {
            is RepoRequestState.Loading -> LoadingScreen(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp, top = 20.dp))
            is RepoRequestState.Success -> MovieGrid(
                movies = movieState.movies.collectAsLazyPagingItems(),
                onMovieCardPressed = onMovieCardPressed,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp, top = 20.dp)
            )
            is RepoRequestState.Error -> ErrorScreen(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp, top = 20.dp))
        }

        MovieDetail(
            movie = uiState.currentSelectedMovie,
            modifier = Modifier.weight(1f),
        )
    }
}

/**
 * The home screen displaying the loading message.
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.loading)
        )
    }
}

/**
 * The home screen displaying error message
 */
@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(stringResource(R.string.loading_failed))
    }
}

@Composable
fun MovieGrid(
    movies: LazyPagingItems<Movie>,
    onMovieCardPressed: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(128.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(8.dp)
    ) {
        items(movies.itemCount) { index ->
            MovieCard(
                movie = movies[index]!!,
                onMovieCardPressed = onMovieCardPressed
            )
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