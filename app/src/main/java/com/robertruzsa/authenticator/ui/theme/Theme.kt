package com.robertruzsa.authenticator.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColors(
    primary = BlueColor,
    onPrimary = Color.White,
    secondary = BlueColor,
    onSecondary = Color.White,
    background = LightGrayColor,
    onBackground = Color.Gray,
    surface = Color.White,
    onSurface = Color.Black,
    error = RedErrorDark,
    secondaryVariant = LightGrayVariantColor
)

private val DarkColorPalette = darkColors(
    primary = OrangeColor,
    onPrimary = Color.Black,
    secondary = OrangeColor,
    onSecondary = Color.Black,
    background = DarkBackgroundColor,
    onBackground = Color.Gray,
    surface = DarkSurfaceColor,
    onSurface = DarkTextColor,
    error = RedErrorLight,
    secondaryVariant = Color.Gray
)

@Composable
fun AuthenticatorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
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
