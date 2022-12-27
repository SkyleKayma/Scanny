package fr.skyle.scanny.ui.core

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import fr.skyle.scanny.theme.SCAppTheme

@Composable
fun TitleText(
    textId: Int,
    modifier: Modifier = Modifier,
    color: Color? = null,
    style: TextStyle? = null,
) {
    Text(
        modifier = modifier,
        text = stringResource(id = textId),
        color = color ?: SCAppTheme.colors.textPrimary,
        style = style ?: MaterialTheme.typography.h2
    )
}