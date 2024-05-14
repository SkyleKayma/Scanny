package fr.skyle.scanny.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import fr.skyle.scanny.R

val lightMaterialColorScheme: ColorScheme
    @Composable
    get() = lightColorScheme(
        primary = colorResource(id = R.color.primary),
        onPrimary = colorResource(id = R.color.black),
        primaryContainer = colorResource(id = R.color.primary),
        onPrimaryContainer = colorResource(id = R.color.black),

        secondary = colorResource(id = R.color.secondary),
        onSecondary = colorResource(id = R.color.white),
        secondaryContainer = colorResource(id = R.color.secondary),
        onSecondaryContainer = colorResource(id = R.color.white),

        error = colorResource(id = R.color.error),
        onError = colorResource(id = R.color.white),
        errorContainer = colorResource(id = R.color.error),
        onErrorContainer = colorResource(id = R.color.white),

        background = colorResource(id = R.color.primary),
        onBackground = colorResource(id = R.color.black),

        surface = colorResource(id = R.color.primary),
        onSurface = colorResource(id = R.color.black),
        surfaceVariant = colorResource(id = R.color.primary),
        onSurfaceVariant = colorResource(id = R.color.black)
    )

val darkMaterialColorScheme: ColorScheme
    @Composable
    get() = lightColorScheme(
        primary = colorResource(id = R.color.primary),
        onPrimary = colorResource(id = R.color.black),
        primaryContainer = colorResource(id = R.color.primary),
        onPrimaryContainer = colorResource(id = R.color.black),

        secondary = colorResource(id = R.color.secondary),
        onSecondary = colorResource(id = R.color.white),
        secondaryContainer = colorResource(id = R.color.secondary),
        onSecondaryContainer = colorResource(id = R.color.white),

        error = colorResource(id = R.color.error),
        onError = colorResource(id = R.color.white),
        errorContainer = colorResource(id = R.color.error),
        onErrorContainer = colorResource(id = R.color.white),

        background = colorResource(id = R.color.primary),
        onBackground = colorResource(id = R.color.black),

        surface = colorResource(id = R.color.primary),
        onSurface = colorResource(id = R.color.black),
        surfaceVariant = colorResource(id = R.color.primary),
        onSurfaceVariant = colorResource(id = R.color.black)
    )