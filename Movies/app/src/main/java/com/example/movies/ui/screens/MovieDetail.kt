package com.example.movies.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movies.R
import com.example.movies.model.Movie


// Configuration: https://developers.themoviedb.org/3/configuration/get-api-configuration
private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"
private const val IMAGE_SIZE = "w780"


@Composable
fun MovieDetail(
    movie: Movie?,
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit = {},
    isFullScreen: Boolean = false
) {
    Card(
        elevation = 16.dp,
        modifier = modifier
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        if (movie != null) {
            Column(
                modifier = modifier
                    .fillMaxSize()
            ) {
                if (movie.backdrop_path != null) {
                    Box(
                        modifier = Modifier
                            .height(250.dp)
                            .fillMaxWidth()
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data("$IMAGE_BASE_URL$IMAGE_SIZE${movie.backdrop_path}")
                                .crossfade(true)
                                .build(),
                            error = painterResource(R.drawable.ic_broken_image),
                            placeholder = painterResource(R.drawable.loading_img),
                            contentDescription = movie.title,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                Text(
                    text = if (movie.releaseDate.isNotEmpty())
                        "${movie.title} (${movie.releaseDate.slice(0..3)})"
                    else
                        movie.title,
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 8.dp)
                )

                Text(
                    text = movie.overview,
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
                )

//                if (isFullScreen) {
//                    Spacer(modifier = Modifier.weight(1f))
//
//                    IconButton(
//                        onClick = onBackPressed,
//                        modifier = Modifier
//                            .padding(start = 8.dp, bottom = 8.dp)
//                    ) {
//                        Icon(
//                            imageVector = Icons.Outlined.ArrowBack,
//                            contentDescription = stringResource(R.string.go_back),
//                            tint = MaterialTheme.colors.onSurface
//                        )
//                    }
//                }
            }
        }
    }
}