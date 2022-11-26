package fr.skyle.scanny.ui.core

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import fr.skyle.scanny.R

@Composable
fun TitleText(
    textId: Int,
    modifier: Modifier = Modifier,
    colorId: Int? = null,
    style: TextStyle? = null,
) {
    Text(
        modifier = modifier,
        text = stringResource(id = textId),
        color = colorResource(id = colorId ?: R.color.sc_title),
        style = style ?: MaterialTheme.typography.h2
    )
}