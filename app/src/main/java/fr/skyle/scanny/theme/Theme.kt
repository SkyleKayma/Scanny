package fr.skyle.scanny.theme

import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.colorResource
import fr.skyle.scanny.R

@Composable
fun SCTheme(content: @Composable () -> Unit) {
    val colors = SCColors(
        transparent = colorResource(id = R.color.sc_transparent),
        primary = colorResource(id = R.color.sc_primary),
        error = colorResource(id = R.color.sc_error),
        success = colorResource(id = R.color.sc_success),
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
        backgroundDisabledAlpha = colorResource(id = R.color.sc_background_disabled).copy(alpha = 0.3f),
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