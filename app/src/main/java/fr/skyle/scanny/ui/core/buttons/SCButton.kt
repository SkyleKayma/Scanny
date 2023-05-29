package fr.skyle.scanny.ui.core.buttons

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.skyle.scanny.R
import fr.skyle.scanny.theme.SCAppTheme
import fr.skyle.scanny.theme.SCTheme


@Composable
fun SCButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = SCAppTheme.colors.backgroundPrimary,
    contentColor: Color = SCAppTheme.colors.textLight,
    isEnabled: Boolean = true
) {
    Button(
        modifier = modifier
            .wrapContentHeight()
            .heightIn(min = 40.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            disabledBackgroundColor = SCAppTheme.colors.backgroundDisabled,
            disabledContentColor = SCAppTheme.colors.textDisabled
        ),
        shape = RoundedCornerShape(10.dp),
        onClick = onClick,
        enabled = isEnabled
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .wrapContentWidth(),
            text = text,
            style = SCAppTheme.typography.button,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Preview
@Composable
fun PreviewScannyButton() {
    SCTheme {
        SCButton(
            text = stringResource(id = R.string.generic_create),
            onClick = {}
        )
    }
}