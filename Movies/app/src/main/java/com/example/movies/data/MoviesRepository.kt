package com.example.movies.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movies.model.Movie
import com.example.movies.network.MoviesApiService
import kotlinx.coroutines.flow.Flow


enum class RequestType(val value: String) {
    TOP_RATED("top_rated"),
    POPULAR("popular"),
    SEARCH("search")
}
interface MoviesRepository {
    fun getTopRatedMovies(): Flow<PagingData<Movie>>
    fun getPopularMovies(): Flow<PagingData<Movie>>
    fun movieSearch(query: String) : Flow<PagingData<Movie>>

    companion object {
        const val NETWORK_PAGE_SIZE = 50
    }

}

class DefaultMoviesRepository(
    private val movieSApiService: MoviesApiService
) : MoviesRepository {

    override fun getTopRatedMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = MoviesRepository.NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviesPagingSource(
                moviesApiService = movieSApiService,
                requestType = RequestType.TOP_RATED,
            ) }
        ).flow
    }

    override fun getPopularMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = MoviesRepository.NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviesPagingSource(
                moviesApiService = movieSApiService,
                requestType = RequestType.POPULAR,
            ) }
        ).flow
    }

    override fun movieSearch(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = MoviesRepository.NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviesPagingSource(
                moviesApiService = movieSApiService,
                requestType = RequestType.SEARCH,
                query = query
            ) }
        ).flow
    }

}