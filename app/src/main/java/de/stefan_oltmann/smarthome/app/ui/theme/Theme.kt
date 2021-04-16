package de.stefan_oltmann.smarthome.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    background = white,
    primary = gray,
    primaryVariant = gray,
    secondary = gray,
    surface = white,
    onPrimary = white,
    onSecondary = black,
    onBackground = black,
    onSurface = black
)

private val LightColorPalette = lightColors(
    background = white,
    primary = gray,
    primaryVariant = gray,
    secondary = gray,
    surface = white,
    onPrimary = white,
    onSecondary = black,
    onBackground = black,
    onSurface = black
)

@Composable
fun SmartHomeAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
