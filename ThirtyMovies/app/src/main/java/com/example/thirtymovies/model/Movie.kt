package com.example.thirtymovies.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Movie(
    val title: String,
    val year: Int,
    val runtime: String,
    val rating: Double,
    val description: String,
    @DrawableRes val imageRes: Int
)