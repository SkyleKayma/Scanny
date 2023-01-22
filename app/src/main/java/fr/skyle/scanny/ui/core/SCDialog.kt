package fr.skyle.scanny.ui.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import fr.skyle.scanny.R
import fr.skyle.scanny.theme.SCAppTheme
import fr.skyle.scanny.theme.SCTheme
import fr.skyle.scanny.ui.core.buttons.SCButton

@Composable
fun SCDialog(
    title: String,
    actionText1: String,
    actionText2: String,
    onClickAction1: () -> Unit,
    onClickAction2: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            Column(
                modifier = Modifier
                    .background(color = SCAppTheme.colors.backgroundLight, shape = RoundedCornerShape(10.dp))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    color = SCAppTheme.colors.primary,
                    style = SCAppTheme.typography.h3,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                SCButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = actionText1,
                    onClick = onClickAction1,
                    contentColor = SCAppTheme.colors.textLight
                )

                Spacer(modifier = Modifier.height(12.dp))

                SCButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = actionText2,
                    onClick = onClickAction2,
                    contentColor = SCAppTheme.colors.textLight
                )

                Spacer(modifier = Modifier.height(12.dp))

                SCButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.generic_cancel),
                    onClick = onDismiss,
                    backgroundColor = SCAppTheme.colors.backgroundDisabled,
                    contentColor = SCAppTheme.colors.textLight
                )
            }
        }
    }
}

@Composable
@Preview
fun PreviewSCDialog() {
    SCTheme {
        SCDialog(
            title = "title",
            actionText1 = "action",
            actionText2 = "action",
            onDismiss = {},
            onClickAction1 = {},
            onClickAction2 = {}
        )
    }
}