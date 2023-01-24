package fr.skyle.scanny.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class SCColors(
    val transparent: Color,
    val primary: Color,
    val error: Color,
    val textPrimary: Color,
    val text: Color,
    val textDark: Color,
    val textLight: Color,
    val textDisabled: Color,
    val textBlack: Color,
    val background: Color,
    val backgroundLight: Color,
    val backgroundPrimary: Color,
    val backgroundDisabled: Color,
    val backgroundIcon: Color,
    val backgroundBlack: Color
)

val LocalColors = staticCompositionLocalOf {
    SCColors(
        transparent = Color.Unspecified,
        primary = Color.Unspecified,
        error = Color.Unspecified,
        textPrimary = Color.Unspecified,
        text = Color.Unspecified,
        textDark = Color.Unspecified,
        textLight = Color.Unspecified,
        textDisabled = Color.Unspecified,
        textBlack = Color.Unspecified,
        background = Color.Unspecified,
        backgroundLight = Color.Unspecified,
        backgroundPrimary = Color.Unspecified,
        backgroundDisabled = Color.Unspecified,
        backgroundIcon = Color.Unspecified,
        backgroundBlack = Color.Unspecified
    )
}