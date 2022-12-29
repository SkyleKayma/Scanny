package fr.skyle.scanny.ui.core

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import fr.skyle.scanny.R
import fr.skyle.scanny.theme.SCAppTheme
import fr.skyle.scanny.theme.SCTheme

@Composable
fun SettingsTitleText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = text,
        color = SCAppTheme.colors.text,
        style = SCAppTheme.typography.body3
    )
}

@Preview
@Composable
fun PreviewTitleText() {
    SCTheme {
        SettingsTitleText(text = stringResource(id = R.string.generate_title))
    }
}