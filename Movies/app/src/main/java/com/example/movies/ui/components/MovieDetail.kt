package com.example.movies.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.inverseOnSurface)
            .fillMaxHeight()
            .padding(top = 8.dp)
    ) {
        OutlinedCard(
            modifier = modifier
                .padding(8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            if (movie != null) {
                Column {
                    if (movie.backdropPath != null) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .height(250.dp)
                                .fillMaxWidth()
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data("$IMAGE_BASE_URL$IMAGE_SIZE${movie.backdropPath}")
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
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 8.dp)
                    )

                    Text(
                        text = if (movie.overview != "") movie.overview else "No description available.",
                        textAlign = TextAlign.Justify,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(
                            top = 8.dp,
                            bottom = 4.dp,
                            start = 16.dp,
                            end = 16.dp
                        )
                    )

                    if (isFullScreen) {
                        IconButton(
                            onClick = onBackPressed,
                            modifier = Modifier
                                .padding(start = 8.dp, bottom = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ArrowBack,
                                contentDescription = stringResource(R.string.go_back),
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }
    }
}