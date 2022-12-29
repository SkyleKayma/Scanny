package fr.skyle.scanny.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


@Immutable
data class SCTypography(
    val h1: TextStyle,
    val h2: TextStyle,
    val h3: TextStyle,
    val body1: TextStyle,
    val body2: TextStyle,
    val body3: TextStyle,
    val subtitle1: TextStyle,
    val subtitle2: TextStyle,
    val button: TextStyle,
    val caption: TextStyle
)

// Set of Material typography styles to start with
val Typography = SCTypography(
    h1 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp
    ),
    h2 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp
    ),
    h3 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp
    ),
    body1 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp
    ),
    body3 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 13.sp
    ),
    subtitle1 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),
    subtitle2 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp
    ),
    button = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    caption = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 12.sp
    )
)

val LocalTypography = staticCompositionLocalOf {
    SCTypography(
        h1 = TextStyle.Default,
        h2 = TextStyle.Default,
        h3 = TextStyle.Default,
        body1 = TextStyle.Default,
        body2 = TextStyle.Default,
        body3 = TextStyle.Default,
        subtitle1 = TextStyle.Default,
        subtitle2 = TextStyle.Default,
        button = TextStyle.Default,
        caption = TextStyle.Default
    )
}