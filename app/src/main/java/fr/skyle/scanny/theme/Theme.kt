package fr.skyle.scanny.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.*
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.res.colorResource
import fr.skyle.scanny.R


@Composable
fun SCTheme(content: @Composable () -> Unit) {
    val colors = SCColors(
        transparent = colorResource(id = R.color.sc_transparent),
        primary = colorResource(id = R.color.sc_primary),
        error = colorResource(id = R.color.sc_primary),
        textPrimary = colorResource(id = R.color.sc_text_primary),
        text = colorResource(id = R.color.sc_text),
        textDark = colorResource(id = R.color.sc_text_dark),
        textLight = colorResource(id = R.color.sc_text_light),
        textDisabled = colorResource(id = R.color.sc_text_disabled),
        textBlack = colorResource(id = R.color.sc_black),
        background = colorResource(id = R.color.sc_background),
        backgroundPrimary = colorResource(id = R.color.sc_background_primary),
        backgroundLight = colorResource(id = R.color.sc_background_light),
        backgroundDisabled = colorResource(id = R.color.sc_background_disabled),
        backgroundIcon = colorResource(id = R.color.sc_background_icon),
        backgroundBlack = colorResource(id = R.color.sc_black)
    )

    val primaryColor = colors.primary
    val backgroundColor = colors.background

    val selectionColors = remember(primaryColor, backgroundColor) {
        TextSelectionColors(
            handleColor = colors.primary,
            backgroundColor = primaryColor.copy(alpha = 0.4f)
        )
    }

    CompositionLocalProvider(
        LocalColors provides colors,
        LocalContentAlpha provides ContentAlpha.high,
        LocalIndication provides rememberRipple(),
        LocalRippleTheme provides MaterialRippleTheme,
        LocalTextSelectionColors provides selectionColors,
        LocalTypography provides Typography
    ) {
        ProvideTextStyle(value = Typography.body1) {
            content()
        }
    }
}

object SCAppTheme {
    val colors: SCColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: SCTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current
}

@Immutable
private object MaterialRippleTheme : RippleTheme {

    @Composable
    override fun defaultColor() = RippleTheme.defaultRippleColor(
        contentColor = LocalContentColor.current,
        lightTheme = MaterialTheme.colors.isLight
    )

    @Composable
    override fun rippleAlpha() = RippleTheme.defaultRippleAlpha(
        contentColor = LocalContentColor.current,
        lightTheme = MaterialTheme.colors.isLight
    )
}