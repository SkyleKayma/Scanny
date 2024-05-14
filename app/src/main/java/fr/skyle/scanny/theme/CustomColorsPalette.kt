package fr.skyle.scanny.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import fr.skyle.scanny.R

@Stable
class CustomColorsPalette(
    val transparent: Color = Color.Unspecified,
    val success: Color = Color.Unspecified,
)

val LightCustomColorsPalette: CustomColorsPalette
    @Composable
    get() = CustomColorsPalette(
        transparent = colorResource(id = R.color.transparent),
        success = colorResource(id = R.color.success),
    )

val DarkCustomColorsPalette: CustomColorsPalette
    @Composable
    get() = CustomColorsPalette(
        transparent = colorResource(id = R.color.transparent),
        success = colorResource(id = R.color.success),
    )

val LocalCustomColorsPalette = staticCompositionLocalOf { CustomColorsPalette() }