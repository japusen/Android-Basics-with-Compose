import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movies.R
import com.example.movies.ui.screens.MovieGrid
import com.example.movies.ui.screens.MoviesUiState

@Composable
fun HomeScreen(
    moviesUiState: MoviesUiState,
    modifier: Modifier = Modifier
) {
    when (moviesUiState) {
        is MoviesUiState.Loading -> LoadingScreen(modifier)
        is MoviesUiState.Success -> MovieGrid(moviesUiState.movies.collectAsLazyPagingItems())
        is MoviesUiState.Error -> ErrorScreen(modifier)
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