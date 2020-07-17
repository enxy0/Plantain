package kek.plantain.ui.theme

import androidx.compose.Composable
import androidx.ui.foundation.isSystemInDarkTheme
import androidx.ui.graphics.Color
import androidx.ui.material.*
import com.google.android.material.color.MaterialColors

private val DarkColorPalette = darkColorPalette(
    primary = purple200,
    primaryVariant = purple700,
    secondary = green400
)

private val LightColorPalette = lightColorPalette(
    primary = purple500,
    primaryVariant = purple700,
    secondary = green400
)

@Composable
val ColorPalette.onSurfaceTwice: Color
    get() = if (isLight) grey200 else dark600

@Composable
fun PlantainTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    MaterialTheme(
        colors = if (darkTheme) DarkColorPalette else LightColorPalette,
        typography = typography,
        shapes = shapes,
        content = content
    )
}

@Composable
internal fun ThemedPreview(
    darkTheme: Boolean = false,
    children: @Composable() () -> Unit
) {
    PlantainTheme(darkTheme = darkTheme) {
        Surface(color = MaterialTheme.colors.background) {
            children()
        }
    }
}