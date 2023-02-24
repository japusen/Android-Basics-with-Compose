package com.example.thirtymovies.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.thirtymovies.R

val Roboto = FontFamily(
    Font(R.font.roboto_light, FontWeight.Light),
    Font(R.font.roboto_thin, FontWeight.Thin),
    Font(R.font.roboto_medium, FontWeight.Medium),
    Font(R.font.roboto_regular, FontWeight.Normal)
)

// Set of Material typography styles to start with
val Typography = Typography(
    defaultFontFamily = Roboto,
    h1 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 48.sp
    ),
    h2 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),
    h3 = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 16.sp
    )
)