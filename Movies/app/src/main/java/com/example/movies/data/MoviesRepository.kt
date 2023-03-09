package com.example.movies.data

import com.example.movies.model.Movie
import com.example.movies.network.MoviesApiService

interface MoviesRepository {
    suspend fun getMovies(filter: String): List<Movie>
}

class DefaultMoviesRepository(
    private val movieSApiService: MoviesApiService
) : MoviesRepository {
    override suspend fun getMovies(filter: String): List<Movie> {
        return movieSApiService.getMovies(filter).results
    }

}