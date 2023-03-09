package com.example.movies.data

import com.example.movies.model.Movie
import com.example.movies.network.MoviesApi
import com.example.movies.ui.screens.Filter

interface MoviesRepository {
    suspend fun getMovies(filter: String): List<Movie>
}

class DefaultMoviesRepository : MoviesRepository {
    override suspend fun getMovies(filter: String): List<Movie> {
        return MoviesApi.retrofitService.getMovies(filter).results
    }

}