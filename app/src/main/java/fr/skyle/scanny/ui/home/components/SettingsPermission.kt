package fr.skyle.scanny.ui.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.skyle.scanny.R
import fr.skyle.scanny.theme.SCAppTheme
import fr.skyle.scanny.theme.SCTheme
import fr.skyle.scanny.ui.core.buttons.SCButton

@Composable
fun SettingsPermission(
    navigateToSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.wrapContentSize()) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 24.dp),
            text = stringResource(id = R.string.permission_camera_required_message_refused),
            style = SCAppTheme.typography.body1,
            color = SCAppTheme.colors.textLight,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        SCButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.permission_settings),
            onClick = navigateToSettings
        )
    }
}

@Preview
@Composable
fun PreviewSettingsPermission() {
    SCTheme {
        SettingsPermission(
            navigateToSettings = {}
        )
    }
}