package com.example.movies.network


import com.example.movies.model.MovieList
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit

import retrofit2.http.GET
import retrofit2.http.Path

private const val API_KEY = "d75b819562c1b230a42cb36cd1022dfd"
private const val BASE_URL = "https://api.themoviedb.org/3/"
// Configuration: https://developers.themoviedb.org/3/configuration/get-api-configuration
private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"
private const val IMAGE_SIZE = "https://image.tmdb.org/t/p/"



private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
    .baseUrl(BASE_URL)
    .build()

interface MoviesApiService {
    @GET("movie/{filter}?api_key=$API_KEY")
    suspend fun getMovies(
        @Path("filter") filter: String
    ): MovieList
}

object MoviesApi {
    val retrofitService : MoviesApiService by lazy {
        retrofit.create(MoviesApiService::class.java)
    }
}