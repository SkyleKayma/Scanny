package fr.skyle.scanny.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import fr.skyle.scanny.R

@Composable
fun ScannyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColors(
            primary = colorResource(id = R.color.purple_80),
            primaryVariant = colorResource(id = R.color.purple_grey_80),
            secondary = colorResource(id = R.color.pink_80)
        )
    } else {
        lightColors(
            primary = colorResource(id = R.color.purple_40),
            primaryVariant = colorResource(id = R.color.purple_grey_40),
            secondary = colorResource(id = R.color.pink_40)
        )
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}