package fr.skyle.scanny.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.colorResource
import fr.skyle.scanny.R


@Composable
fun SCTheme(content: @Composable () -> Unit) {
    val colors = SCColors(
        primary = colorResource(id = R.color.sc_primary),
        primaryVariant = colorResource(id = R.color.sc_primary_variant),
        nuance100 = colorResource(id = R.color.sc_nuance_100),
        nuance90 = colorResource(id = R.color.sc_nuance_90),
        nuance80 = colorResource(id = R.color.sc_nuance_80),
        nuance70 = colorResource(id = R.color.sc_nuance_70),
        nuance60 = colorResource(id = R.color.sc_nuance_60),
        nuance40 = colorResource(id = R.color.sc_nuance_40),
        nuance30 = colorResource(id = R.color.sc_nuance_30),
        nuance20 = colorResource(id = R.color.sc_nuance_20),
        nuance10 = colorResource(id = R.color.sc_nuance_10),
        error = colorResource(id = R.color.sc_error),
        warning = colorResource(id = R.color.sc_warning),
        success = colorResource(id = R.color.sc_success),
    )

    val primaryColor = colors.primary
    val backgroundColor = colors.nuance90

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
    override fun defaultColor() =
        RippleTheme.defaultRippleColor(
            contentColor = LocalContentColor.current,
            lightTheme = MaterialTheme.colors.isLight
        )

    @Composable
    override fun rippleAlpha() =
        RippleTheme.defaultRippleAlpha(
            contentColor = LocalContentColor.current,
            lightTheme = MaterialTheme.colors.isLight
        )
}