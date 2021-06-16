package dev.sourabh.programmerhumor.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Primary,
    surface = BackgroundDark,
    secondary = Secondary,
    background = BackgroundDark,
    onBackground = Color.White,
    onSurface = Color.White
)

/*private val LightColorPalette = lightColors(
    primary = Primary,
    secondary = Secondary,
    background = Color.White
)*/

@Composable
fun ProgrammerHumorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = DarkColorPalette/* if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }*/

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}