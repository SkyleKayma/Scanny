package fr.skyle.scanny.ui.scan.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import fr.skyle.scanny.R
import fr.skyle.scanny.theme.SCAppTheme
import fr.skyle.scanny.theme.SCTheme

@Composable
fun CameraFilter(
    isFlashEnabled: Boolean,
    onFlashClicked: () -> Unit,
    onGalleryClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = SCAppTheme.colors.backgroundBlack.copy(alpha = 0.5f)

    ConstraintLayout(modifier = modifier) {
        val (topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner,
            topRow, bottomRow, leftColumn, rightColumn, centerSquare, topText, buttons) = createRefs()

        // Center square
        Spacer(
            modifier = Modifier
                .size(250.dp)
                .constrainAs(centerSquare) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )

        // Borders
        Spacer(
            modifier = Modifier
                .background(backgroundColor)
                .constrainAs(topRow) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(centerSquare.top)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        )

        Spacer(
            modifier = Modifier
                .background(backgroundColor)
                .constrainAs(bottomRow) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(centerSquare.bottom)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        )

        Spacer(
            modifier = Modifier
                .background(backgroundColor)
                .constrainAs(leftColumn) {
                    start.linkTo(parent.start)
                    end.linkTo(centerSquare.start)
                    top.linkTo(topRow.bottom)
                    bottom.linkTo(bottomRow.top)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        )

        Spacer(
            modifier = Modifier
                .background(backgroundColor)
                .constrainAs(rightColumn) {
                    start.linkTo(centerSquare.end)
                    end.linkTo(parent.end)
                    top.linkTo(topRow.bottom)
                    bottom.linkTo(bottomRow.top)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        )

        Icon(
            modifier = Modifier
                .size(25.dp)
                .constrainAs(topLeftCorner) {
                    top.linkTo(topRow.bottom, (-4).dp)
                    start.linkTo(leftColumn.end, (-4).dp)
                },
            painter = painterResource(id = R.drawable.camera_filter_top_left),
            contentDescription = "",
            tint = SCAppTheme.colors.backgroundLight
        )

        Icon(
            modifier = Modifier
                .size(25.dp)
                .constrainAs(topRightCorner) {
                    top.linkTo(topRow.bottom, (-4).dp)
                    end.linkTo(rightColumn.start, (-4).dp)
                },
            painter = painterResource(id = R.drawable.camera_filter_top_right),
            contentDescription = "",
            tint = SCAppTheme.colors.backgroundLight
        )

        Icon(
            modifier = Modifier
                .size(25.dp)
                .constrainAs(bottomLeftCorner) {
                    bottom.linkTo(bottomRow.top, (-4).dp)
                    start.linkTo(leftColumn.end, (-4).dp)
                },
            painter = painterResource(id = R.drawable.camera_filter_bottom_left),
            contentDescription = "",
            tint = SCAppTheme.colors.backgroundLight
        )

        Icon(
            modifier = Modifier
                .size(25.dp)
                .constrainAs(bottomRightCorner) {
                    bottom.linkTo(bottomRow.top, (-4).dp)
                    end.linkTo(rightColumn.start, (-4).dp)
                },
            painter = painterResource(id = R.drawable.camera_filter_bottom_right),
            contentDescription = "",
            tint = SCAppTheme.colors.backgroundLight
        )

        // Top Text
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(topText) {
                    linkTo(top = parent.top, bottom = centerSquare.top, bottomMargin = 24.dp, bias = 1.0f)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                },
            text = "Scanner un QRCode",
            color = SCAppTheme.colors.textLight,
            textAlign = TextAlign.Center,
            style = SCAppTheme.typography.h3
        )

        val flashIconId = if (isFlashEnabled) R.drawable.ic_flashlight_off else R.drawable.ic_flashlight_on

        // Buttons
        Row(
            modifier = modifier
                .fillMaxWidth()
                .constrainAs(buttons) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(centerSquare.bottom)
                    bottom.linkTo(parent.bottom)
                },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(100))
                    .background(SCAppTheme.colors.backgroundLight.copy(alpha = 0.3f)),
                onClick = onFlashClicked
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = flashIconId),
                    contentDescription = "",
                    tint = SCAppTheme.colors.textLight
                )
            }

            Spacer(modifier = Modifier.width(40.dp))

            IconButton(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(100))
                    .background(SCAppTheme.colors.backgroundLight.copy(alpha = 0.3f)),
                onClick = onGalleryClicked
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_picture),
                    contentDescription = "",
                    tint = SCAppTheme.colors.textLight
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewCameraFilter() {
    SCTheme {
        CameraFilter(
            onFlashClicked = {},
            onGalleryClicked = {},
            isFlashEnabled = false
        )
    }
}