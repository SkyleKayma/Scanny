package fr.skyle.scanny.ui.core

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.skyle.scanny.R
import fr.skyle.scanny.theme.SCAppTheme
import fr.skyle.scanny.theme.SCTheme

@Composable
fun SCTopAppBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    onClickHomeButton: (() -> Unit)? = null,
    actionIconId: Int? = null,
    actionIconColor: Color? = null,
    onClickAction: (() -> Unit)? = null,
    isComingFromDown: Boolean = false
) {
    TopAppBar(
        backgroundColor = SCAppTheme.colors.transparent,
        modifier = modifier.fillMaxWidth(),
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            onClickHomeButton?.let {
                val startIcon = painterResource(
                    id = if (!isComingFromDown) {
                        R.drawable.ic_arrow_left
                    } else R.drawable.ic_arrow_down
                )

                IconButton(onClick = it) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = startIcon,
                        contentDescription = null,
                        tint = SCAppTheme.colors.textDark
                    )
                }
            }

            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp),
                text = title ?: "",
                color = SCAppTheme.colors.textDark,
                style = SCAppTheme.typography.h3,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            onClickAction?.let {
                IconButton(onClick = it) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = actionIconId ?: R.drawable.ic_notifications),
                        contentDescription = null,
                        tint = actionIconColor ?: SCAppTheme.colors.textDark
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun SCTopAppBarPreview() {
    SCTheme {
        SCTopAppBar(
            title = stringResource(id = R.string.app_name),
        )
    }
}

@Composable
@Preview
fun SCTopAppBarPreviewWithHomeButton() {
    SCTheme {
        SCTopAppBar(
            title = stringResource(id = R.string.app_name),
            onClickHomeButton = {}
        )
    }
}

@Composable
@Preview
fun SCTopAppBarPreviewWithHomeAndAction() {
    SCTheme {
        SCTopAppBar(
            title = stringResource(id = R.string.app_name),
            onClickHomeButton = {},
            actionIconId = R.drawable.ic_arrow_right,
            onClickAction = {}
        )
    }
}