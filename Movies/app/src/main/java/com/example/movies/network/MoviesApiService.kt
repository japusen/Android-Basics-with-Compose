package com.example.movies.network

import com.example.movies.model.MovieList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


private const val API_KEY = "d75b819562c1b230a42cb36cd1022dfd"

interface MoviesApiService {
    @GET("movie/{filter}?api_key=$API_KEY")
    suspend fun getMovies(
        @Path("filter") filter: String
    ): MovieList

    @GET("search/movie?api_key=$API_KEY")
    suspend fun movieSearch(
        @Query("query") query: String,
        @Query("include_adult") adult: Boolean = false
    ) : MovieList
}