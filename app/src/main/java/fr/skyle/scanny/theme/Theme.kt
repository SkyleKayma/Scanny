package fr.skyle.scanny.theme

import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.res.colorResource
import fr.skyle.scanny.R

@Composable
fun ScannyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors =
        lightColors(
            primary = colorResource(id = R.color.sc_primary),
            primaryVariant = colorResource(id = R.color.sc_primary_variant_1),
            secondary = colorResource(id = R.color.sc_primary),
            secondaryVariant = colorResource(id = R.color.sc_primary_variant_1),
            background = colorResource(id = R.color.sc_background),
            surface = colorResource(id = R.color.sc_white)
        )

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes
    ) {
        CompositionLocalProvider(
            LocalOverscrollConfiguration.provides(null),
            content = content
        )
    }
}