package fr.skyle.scanny.ui.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.skyle.scanny.R
import fr.skyle.scanny.theme.ScannyTheme

@Composable
fun ScannyTopAppBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    actionIconId: Int? = null,
    hasHomeButton: Boolean = true,
    onClickAction: (() -> Unit)? = null,
    onClickHomeButton: () -> Unit,
    isComingFromDown: Boolean = true
) {
    TopAppBar(
        backgroundColor = colorResource(id = R.color.sc_transparent),
        modifier = modifier.fillMaxWidth(),
        elevation = 0.dp
    ) {
        Box {
            if (hasHomeButton) {
                IconButton(
                    onClick = {
                        onClickHomeButton()
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = if (!isComingFromDown) {
                            painterResource(id = R.drawable.ic_arrow_left)
                        } else painterResource(id = R.drawable.ic_arrow_down),
                        contentDescription = null,
                        tint = MaterialTheme.colors.secondary
                    )
                }
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .padding(40.dp, 0.dp),
                text = title ?: "",
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.h1,
                textAlign = TextAlign.Center
            )

            if (actionIconId != null) {
                IconButton(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    onClick = {
                        onClickAction?.invoke()
                    }) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = actionIconId),
                        contentDescription = null,
                        tint = MaterialTheme.colors.secondary
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun ScannyTopAppBarPreview() {
    ScannyTheme {
        ScannyTopAppBar(
            title = stringResource(id = R.string.app_name),
            actionIconId = null,
            hasHomeButton = false,
            modifier = Modifier,
            onClickAction = {},
            onClickHomeButton = {}
        )
    }
}

@Composable
@Preview
fun ScannyTopAppBarPreviewWithHomeButton() {
    ScannyTheme {
        ScannyTopAppBar(
            title = stringResource(id = R.string.app_name),
            actionIconId = null,
            hasHomeButton = true,
            modifier = Modifier,
            onClickAction = {},
            onClickHomeButton = {}
        )
    }
}

@Composable
@Preview
fun ScannyTopAppBarPreviewWithHomeAndAction() {
    ScannyTheme {
        ScannyTopAppBar(
            title = stringResource(id = R.string.app_name),
            actionIconId = R.drawable.ic_favorite,
            hasHomeButton = true,
            modifier = Modifier,
            onClickAction = {},
            onClickHomeButton = {}
        )
    }
}