package com.example.movies.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movies.R
import com.example.movies.model.Movie

// Configuration: https://developers.themoviedb.org/3/configuration/get-api-configuration
private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"
private const val IMAGE_SIZE = "w780"

@Composable
fun MovieGrid(
    movies: List<Movie>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        items(movies) { movie ->
            MovieCard(movie = movie)
        }
    }
}

@Composable
fun MovieCard(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = 16.dp,
        modifier = Modifier.clickable { }
    ) {
        if (movie.posterPath != null) {
            MoviePoster(movie)
        } else {
            MovieNoPoster(movie)
        }
    }
}

@Composable
fun MoviePoster(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .height(256.dp)
            .width(180.dp)
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
        modifier = Modifier
            .height(256.dp)
            .width(180.dp)
            .background(Color.Black)
    ) {
        Text(
            text = "${movie.title} (${movie.releaseDate.slice(0..3)})",
            style = MaterialTheme.typography.h5,
            color = Color.White
        )
    }
}

@Composable
fun MovieDetail(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(3.dp),
        modifier = Modifier
            .padding(6.dp)
    ) {
        Text(
            text = "${movie.title} (${movie.releaseDate})",
            style = MaterialTheme.typography.h2
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Votes: ${movie.vote_count}",
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Score: ${movie.voteAverage} / 10",
                style = MaterialTheme.typography.h3
            )
        }
        Text(
            text = movie.overview,
            style = MaterialTheme.typography.h3,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize()
        )
    }
}