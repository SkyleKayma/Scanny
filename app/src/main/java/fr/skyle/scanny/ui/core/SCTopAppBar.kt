package fr.skyle.scanny.ui.core

import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.text.style.TextAlign
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
        Box {
            onClickHomeButton?.let {
                IconButton(onClick = it) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = if (!isComingFromDown) {
                            painterResource(id = R.drawable.ic_arrow_left)
                        } else painterResource(id = R.drawable.ic_arrow_down),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .padding(40.dp, 0.dp),
                text = title ?: "",
                color = SCAppTheme.colors.textPrimary,
                style = SCAppTheme.typography.menu,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            if (actionIconId != null) {
                IconButton(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    onClick = {
                        onClickAction?.invoke()
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = actionIconId),
                        contentDescription = null,
                        tint = actionIconColor ?: SCAppTheme.colors.textPrimary
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
            modifier = Modifier,
            title = stringResource(id = R.string.app_name),
            onClickHomeButton = null,
            actionIconId = null,
            onClickAction = {}
        )
    }
}

@Composable
@Preview
fun SCTopAppBarPreviewWithHomeButton() {
    SCTheme {
        SCTopAppBar(
            modifier = Modifier,
            title = stringResource(id = R.string.app_name),
            onClickHomeButton = {},
            actionIconId = null,
            onClickAction = {}
        )
    }
}

@Composable
@Preview
fun SCTopAppBarPreviewWithHomeAndAction() {
    SCTheme {
        SCTopAppBar(
            modifier = Modifier,
            title = stringResource(id = R.string.app_name),
            onClickHomeButton = {},
            actionIconId = R.drawable.ic_arrow_right,
            onClickAction = {}
        )
    }
}