package com.example.thirtymovies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thirtymovies.data.MovieRepository
import com.example.thirtymovies.model.Movie
import com.example.thirtymovies.ui.theme.ThirtyMoviesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThirtyMoviesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ThirtyMoviesApp()
                }
            }
        }
    }
}


@Composable
fun ThirtyMoviesApp() {
    Scaffold(
        topBar = {
            MovieTopAppBar()
        }
    ) { paddingValues ->
        MovieGrid(
            movies = MovieRepository.movies,
            modifier = Modifier
                .padding(paddingValues)
        )
    }
}

@Composable
fun MovieTopAppBar(
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Text(
            text = stringResource(R.string.app_name),
            fontSize = 24.sp
        )
    }
}

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
    var isPosterVisible by remember { mutableStateOf(true) }
    Card(
        elevation = 16.dp,
        modifier = Modifier
    ) {
        Box(
            modifier = Modifier
                .height(256.dp)
                .width(180.dp)
                .clickable(onClick = { isPosterVisible = !isPosterVisible })
        ) {
            AnimatedVisibility (
                visible = !isPosterVisible,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                MovieInfo(
                    movie = movie,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            AnimatedVisibility(
                visible = isPosterVisible,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Image(
                    painter = painterResource(movie.imageRes),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                )
            }
        }
    }
}

@Composable
fun MovieInfo(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(3.dp),
        modifier = Modifier
            .padding(6.dp)
    ) {
        Text(
            text = "${movie.title} (${movie.year})",
            style = MaterialTheme.typography.h2
        )
        
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = movie.runtime,
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${movie.rating} / 10",
                style = MaterialTheme.typography.h3
            )
        }
        Text(
            text = movie.description,
            style = MaterialTheme.typography.h3,
            modifier = Modifier.fillMaxWidth()
                .fillMaxSize()
        )
    }
}

@Preview
@Composable
fun MovieTopAppBarPreview() {
    ThirtyMoviesTheme {
        MovieTopAppBar()
    }
}

@Preview()
@Composable
fun MovieGridPreview() {
    val movies = MovieRepository.movies
    ThirtyMoviesTheme {
        MovieGrid(movies = movies)
    }
}

@Preview(showBackground = true)
@Composable
fun MoviePreview() {
    val movie = MovieRepository.movies[0]
    ThirtyMoviesTheme {
        MovieCard(movie = movie)
    }
}