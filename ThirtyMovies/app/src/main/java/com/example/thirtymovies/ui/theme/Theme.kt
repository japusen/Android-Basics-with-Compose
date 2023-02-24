package com.example.thirtymovies.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@SuppressLint("ConflictingOnColor")
private val DarkColorPalette = darkColors(
    background = Grey900,
    surface = Grey900,
    onSurface = White,
    primary = Grey900,
    onPrimary = White,
)

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
    background = Grey300,
    surface = White,
    onSurface = Black,
    primary = Grey300,
    onPrimary = Black,
)

@Composable
fun ThirtyMoviesTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}