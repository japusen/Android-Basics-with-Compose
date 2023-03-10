package com.example.movies.data

import com.example.movies.model.Movie
import com.example.movies.network.MoviesApiService


enum class Filter(val value: String) {
    TOP_RATED("top_rated"),
    POPULAR("popular")
}
interface MoviesRepository {
    suspend fun getTopRatedMovies(): List<Movie>
    suspend fun getPopularMovies(): List<Movie>
    suspend fun movieSearch(query: String) : List<Movie>
}

class DefaultMoviesRepository(
    private val movieSApiService: MoviesApiService
) : MoviesRepository {

    override suspend fun getTopRatedMovies(): List<Movie> {
        return movieSApiService.getMovies(Filter.TOP_RATED.value).results
    }

    override suspend fun getPopularMovies(): List<Movie> {
        return movieSApiService.getMovies(Filter.POPULAR.value).results
    }

    override suspend fun movieSearch(query: String): List<Movie> {
        return movieSApiService.movieSearch(query).results
    }

}