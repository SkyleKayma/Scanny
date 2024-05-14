package fr.skyle.scanny.theme

import android.app.Activity
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun ScannyTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    useDynamicColors: Boolean = true,
    content: @Composable () -> Unit
) {
    // Default palette
    val colorScheme = when {
//        useDynamicColors && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            if (useDarkTheme) {
//                dynamicDarkColorScheme(context = LocalContext.current)
//            } else {
//                dynamicLightColorScheme(context = LocalContext.current)
//            }
//        }

        useDarkTheme -> darkMaterialColorScheme
        else -> lightMaterialColorScheme
    }

    // Custom palette
    val customColorsPalette =
        if (useDarkTheme) {
            DarkCustomColorsPalette
        } else {
            LightCustomColorsPalette
        }

    // Ripple
    val rippleIndication = rememberRipple()

    // Update StatusBar based on theme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.navigationBarColor = customColorsPalette.transparent.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !useDarkTheme
        }
    }

    CompositionLocalProvider(
        LocalCustomColorsPalette provides customColorsPalette,
        LocalIndication provides rippleIndication,
        LocalRippleTheme provides CustomRippleTheme,
        LocalTypography provides Typography
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content
        )
    }
}

val MaterialTheme.customColorsPalette: CustomColorsPalette
    @Composable
    @ReadOnlyComposable
    get() = LocalCustomColorsPalette.current

val MaterialTheme.customTypography: CustomColorsPalette
    @Composable
    @ReadOnlyComposable
    get() = LocalCustomColorsPalette.current
