package fr.skyle.scanny.ui.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import fr.skyle.scanny.R
import fr.skyle.scanny.ext.pxToDp
import fr.skyle.scanny.theme.SCAppTheme
import fr.skyle.scanny.theme.SCTheme

@Composable
fun SCTopAppBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    backgroundColor: Color? = null,
    rightActionContent: @Composable (() -> Unit)? = null,
    leftActionContent: @Composable (() -> Unit)? = null
) {
    // Density
    val density = LocalDensity.current

    var leftActionButtonsSize by remember { mutableStateOf(IntSize.Zero) }
    var rightActionButtonsSize by remember { mutableStateOf(IntSize.Zero) }

    TopAppBar(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = backgroundColor ?: SCAppTheme.colors.nuance90,
        elevation = 0.dp
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (leftActionView, titleView, rightActionView) = createRefs()

            leftActionContent?.let {
                Box(
                    modifier = Modifier
                        .constrainAs(leftActionView) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            width = Dimension.wrapContent
                        }
                        .onGloballyPositioned {
                            leftActionButtonsSize = it.size
                        }
                ) {
                    it()
                }
            }

            Text(
                modifier = Modifier
                    .constrainAs(titleView) {
                        val maxMargin = maxOf(leftActionButtonsSize.width.pxToDp(density), rightActionButtonsSize.width.pxToDp(density))

                        start.linkTo(parent.start, margin = maxMargin)
                        bottom.linkTo(parent.bottom)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end, margin = maxMargin)
                        width = Dimension.fillToConstraints
                    },
                text = title ?: "",
                color = SCAppTheme.colors.nuance10,
                style = SCAppTheme.typography.h3,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            rightActionContent?.let {
                Box(
                    modifier = Modifier
                        .constrainAs(rightActionView) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                            width = Dimension.wrapContent
                        }
                        .onGloballyPositioned {
                            rightActionButtonsSize = it.size
                        }
                ) {
                    it()
                }
            }
        }
    }
}

@Composable
fun SCTopAppBarWithHomeButton(
    modifier: Modifier = Modifier,
    title: String? = null,
    actionIconColor: Color? = null,
    backgroundColor: Color? = null,
    onClickHomeButton: (() -> Unit)? = null,
    rightActionContent: @Composable (() -> Unit)? = null,
) {
    SCTopAppBar(
        modifier = modifier,
        title = title,
        backgroundColor = backgroundColor,
        leftActionContent = {
            IconButton(
                onClick = {
                    onClickHomeButton?.invoke()
                }
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_arrow_left),
                    contentDescription = null,
                    tint = actionIconColor ?: SCAppTheme.colors.nuance10
                )
            }
        },
        rightActionContent = rightActionContent
    )
}

@Preview
@Composable
private fun PreviewSCTopAppBar() {
    SCTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(SCAppTheme.colors.nuance90)
        ) {
            SCTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = "Title"
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSCTopAppBarWithHomeButton() {
    SCTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(SCAppTheme.colors.nuance90)
        ) {
            SCTopAppBarWithHomeButton(
                modifier = Modifier.fillMaxWidth(),
                title = "Title",
                onClickHomeButton = {}
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSCTopAppBarWithEndActions() {
    SCTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(SCAppTheme.colors.nuance90)
        ) {
            SCTopAppBarWithHomeButton(
                modifier = Modifier.fillMaxWidth(),
                title = "Title",
                onClickHomeButton = {},
                rightActionContent = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(id = R.drawable.ic_settings),
                            contentDescription = null,
                            tint = SCAppTheme.colors.nuance100
                        )
                    }
                }
            )
        }
    }
}