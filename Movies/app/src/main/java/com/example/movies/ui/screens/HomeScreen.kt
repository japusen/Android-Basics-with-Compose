import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.movies.model.Movie
import com.example.movies.ui.screens.*
import com.example.movies.ui.utils.ContentType
import com.example.movies.ui.utils.NavigationType

@Composable
fun HomeScreen(
    navigationType: NavigationType,
    contentType: ContentType,
    uiState: MoviesUiState,
    movieListState: RepoRequestState,
    searchResultsState: RepoRequestState,
    onMovieCardPressed: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {

    if (navigationType == NavigationType.PERMANENT_NAVIGATION_DRAWER) {
        // Tablet
        AppContent(
            navigationType = navigationType,
            contentType = contentType,
            uiState = uiState,
            movieListState = movieListState,
            searchResultsState = searchResultsState,
            onMovieCardPressed = onMovieCardPressed
        )
    } else {
        if (uiState.isShowingMovieDetail) {
            // Detail Screen
            MovieDetail(
                movie = uiState.currentSelectedMovie
            )
        } else {
            // Grid Screen
            AppContent(
                navigationType = navigationType,
                contentType = contentType,
                uiState = uiState,
                movieListState = movieListState,
                searchResultsState = searchResultsState,
                onMovieCardPressed = onMovieCardPressed
            )
        }
    }
}

@Composable
private fun AppContent(
    navigationType: NavigationType,
    contentType: ContentType,
    uiState: MoviesUiState,
    movieListState: RepoRequestState,
    searchResultsState: RepoRequestState,
    onMovieCardPressed: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxSize()) {
        AnimatedVisibility(visible = navigationType == NavigationType.NAVIGATION_RAIL) {
            // Navigation Content
        }
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {

            AnimatedVisibility(visible = navigationType == NavigationType.BOTTOM_NAVIGATION) {
                // Navigation Content
            }

            if (contentType == ContentType.LIST_AND_DETAIL) {
                MovieListAndDetailContent(
                    uiState = uiState,
                    movieState = movieListState,
                    onMovieCardPressed = onMovieCardPressed,
                    modifier = modifier.weight(1f)
                )
            } else {
                MovieListOnlyContent(
                    movieState = movieListState,
                    onMovieCardPressed = onMovieCardPressed,
                    modifier = Modifier.weight(1f)
                )
            }

        }
    }
}