package com.example.movies.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class MovieList(
    val page: Int,
    val results: List<Movie>,
    val total_results: Int,
    val total_pages: Int
)

@Serializable()
data class Movie(
    @SerialName(value = "poster_path") val posterPath: String?,
    val adult: Boolean,
    val overview: String,
    @SerialName(value = "release_date") val releaseDate: String,
    @SerialName("genre_ids") val genre_ids: List<Int>,
    val id: Int,
    val original_title: String,
    val original_language: String,
    val title: String,
    val backdrop_path: String?,
    val popularity: Double,
    val vote_count: Int,
    @SerialName("video") val hasTrailer: Boolean,
    @SerialName(value = "vote_average") val voteAverage: Double,
)

