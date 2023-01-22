package fr.skyle.scanny.ui.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.skyle.scanny.R
import fr.skyle.scanny.theme.SCAppTheme
import fr.skyle.scanny.theme.SCTheme

@Composable
fun SCActionButton(
    iconId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textId: Int? = null,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            modifier = Modifier
                .clip(RoundedCornerShape(100))
                .background(SCAppTheme.colors.primary),
            onClick = onClick
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = iconId),
                contentDescription = "",
                tint = SCAppTheme.colors.textLight
            )
        }

        textId?.let {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(id = it),
                style = SCAppTheme.typography.body2,
                color = SCAppTheme.colors.primary,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun PreviewSCActionButton() {
    SCTheme {
        SCActionButton(
            iconId = R.drawable.ic_action_wifi,
            textId = R.string.scan_action_connect_to_wifi,
            onClick = {}
        )
    }
}