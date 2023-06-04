package fr.skyle.scanny.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class SCColors(
    val primary: Color,
    val primaryVariant: Color,
    val nuance100: Color,
    val nuance90: Color,
    val nuance80: Color,
    val nuance70: Color,
    val nuance60: Color,
    val nuance40: Color,
    val nuance30: Color,
    val nuance20: Color,
    val nuance10: Color,
    val error: Color,
    val warning: Color,
    val success: Color
)

internal val LocalColors = staticCompositionLocalOf {
    SCColors(
        primary = Color.Unspecified,
        primaryVariant = Color.Unspecified,
        nuance100 = Color.Unspecified,
        nuance90 = Color.Unspecified,
        nuance80 = Color.Unspecified,
        nuance70 = Color.Unspecified,
        nuance60 = Color.Unspecified,
        nuance40 = Color.Unspecified,
        nuance30 = Color.Unspecified,
        nuance20 = Color.Unspecified,
        nuance10 = Color.Unspecified,
        error = Color.Unspecified,
        warning = Color.Unspecified,
        success = Color.Unspecified
    )
}