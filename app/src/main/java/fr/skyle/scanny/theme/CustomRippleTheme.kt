package fr.skyle.scanny.theme

import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
object CustomRippleTheme : RippleTheme {

    @Composable
    override fun defaultColor(): Color =
        LocalContentColor.current

    @Composable
    override fun rippleAlpha() =
        RippleAlpha(
            pressedAlpha = 0.16f,
            focusedAlpha = 0.12f,
            draggedAlpha = 0.08f,
            hoveredAlpha = 0.12f
        )
}