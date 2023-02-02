package com.code.block.core.presentation.ui.theme // ktlint-disable filename

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@SuppressLint("ConflictingOnColor")
private val DarkColorScheme = darkColors(
    primary = Blue80,
    onPrimary = Blue20,
    secondary = DarkBlue80,
    onSecondary = DarkBlue20,
    error = Red80,
    onError = Red20,
    background = Grey10,
    onBackground = Grey90,
    surface = Grey10,
    onSurface = Grey80
)

private val LightColorScheme = lightColors(
    primary = Blue40,
    onPrimary = Color.White,
    secondary = DarkBlue40,
    onSecondary = Color.White,
    error = Red40,
    onError = Color.White,
    background = Grey99,
    onBackground = Grey10,
    surface = Grey99,
    onSurface = Grey10
)

@Composable
fun BlockTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val myColorScheme = when {
        isDarkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colors = myColorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
