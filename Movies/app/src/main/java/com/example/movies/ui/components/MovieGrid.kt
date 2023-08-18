package com.example.movies.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
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
    isShowingSearchTab: Boolean,
    query: String,
    movieList: LazyPagingItems<Movie>?,
    gridState: LazyGridState,
    onMovieCardPressed: (Movie) -> Unit,
    onSearchTextChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(MaterialTheme.colorScheme.inverseOnSurface)
            .fillMaxHeight()
    ) {

        SearchBar(
            isShowingSearchTab = isShowingSearchTab,
            query = query,
            onSearchTextChange = onSearchTextChange,
            onSearch = onSearch
        )

        MovieGrid(
            movies = movieList,
            gridState = gridState,
            onMovieCardPressed = onMovieCardPressed,
            modifier = modifier
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
            .padding(start = 6.dp, end = 6.dp, bottom = 6.dp)
    ) {
        if (movies != null) {
            items(movies.itemCount) { index ->
                MovieCard(
                    movie = movies[index]!!, onMovieCardPressed = onMovieCardPressed
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieCard(
    movie: Movie,
    onMovieCardPressed: (Movie) -> Unit,
) {
    Card(onClick = { onMovieCardPressed(movie) }) {
        if (movie.posterPath != null) {
            MoviePoster(
                posterPath = movie.posterPath,
                title = movie.title
            )
        } else {
            MovieNoPoster(
                title = movie.title,
                releaseDate = movie.releaseDate
            )
        }
    }
}

@Composable
fun MoviePoster(
    posterPath: String?,
    title: String,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center, modifier = modifier.height(256.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("$IMAGE_BASE_URL$IMAGE_SIZE${posterPath}")
                .crossfade(true)
                .build(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            contentDescription = title,
            contentScale = ContentScale.FillBounds,
        )
    }
}

@Composable
fun MovieNoPoster(
    title: String,
    releaseDate: String,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(Color.Black)
            .height(256.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = if (releaseDate.isNotEmpty()) "$title (${releaseDate.slice(0..3)})"
            else title,
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}