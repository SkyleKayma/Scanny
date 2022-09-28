package fr.skyle.scanny.ui.createQRWiFi.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import fr.skyle.scanny.ANIMATION_TIME_TRANSITION
import fr.skyle.scanny.R
import fr.skyle.scanny.enums.WifiEncryptionType
import fr.skyle.scanny.ext.toDp
import fr.skyle.scanny.theme.body3

@Composable
fun WifiEncryptionSelector(
    encryptionType: WifiEncryptionType,
    modifier: Modifier = Modifier,
    onEncryptionTypeChanged: (WifiEncryptionType) -> Unit
) {
    val context = LocalContext.current

    var sizeState by remember { mutableStateOf(IntSize(0, 0)) }

    var cell1YOffset by remember { mutableStateOf(0.dp) }
    var cell2YOffset by remember { mutableStateOf(0.dp) }
    var cell3YOffset by remember { mutableStateOf(0.dp) }

    var currentOffsetSelected by remember {
        mutableStateOf(
            when (encryptionType) {
                WifiEncryptionType.WEP -> cell2YOffset
                WifiEncryptionType.WPA_WPA2 -> cell3YOffset
                WifiEncryptionType.NONE -> cell1YOffset
            }
        )
    }
    val animatedOffsetDpState by animateDpAsState(targetValue = currentOffsetSelected, tween(ANIMATION_TIME_TRANSITION))

    Box {
        Box(
            modifier = Modifier
                .offset(x = animatedOffsetDpState)
                .clip(RoundedCornerShape(12.dp))
                .background(colorResource(id = R.color.sc_background_secondary))
                .size(LocalDensity.current.run { sizeState.width.toDp() }, LocalDensity.current.run { sizeState.height.toDp() })
                .align(Alignment.CenterStart)
        ) {}

        Row(
            modifier = modifier
                .border(1.dp, colorResource(id = R.color.sc_black), RoundedCornerShape(12.dp))
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) {
                        onEncryptionTypeChanged(WifiEncryptionType.NONE)
                        currentOffsetSelected = cell1YOffset
                    }
                    .onSizeChanged {
                        sizeState = it
                    }
                    .onGloballyPositioned {
                        cell1YOffset = it.positionInParent().x.toDp(context).dp
                    }
                    .padding(12.dp, 16.dp),
                textAlign = TextAlign.Center,
                maxLines = 1,
                style = MaterialTheme.typography.body3,
                text = stringResource(id = R.string.create_qr_label_wifi_encryption_none),
                color = animateColorAsState(
                    if (currentOffsetSelected == cell1YOffset) {
                        colorResource(id = R.color.sc_white)
                    } else colorResource(id = R.color.sc_black)
                ).value
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) {
                        onEncryptionTypeChanged(WifiEncryptionType.WEP)
                        currentOffsetSelected = cell2YOffset
                    }
                    .onGloballyPositioned {
                        cell2YOffset = it.positionInParent().x.toDp(context).dp
                    }
                    .padding(12.dp, 16.dp),
                textAlign = TextAlign.Center,
                maxLines = 1,
                style = MaterialTheme.typography.body3,
                text = stringResource(id = R.string.create_qr_label_wifi_encryption_wep),
                color = animateColorAsState(
                    if (currentOffsetSelected == cell2YOffset) {
                        colorResource(id = R.color.sc_white)
                    } else colorResource(id = R.color.sc_black)
                ).value
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) {
                        onEncryptionTypeChanged(WifiEncryptionType.WPA_WPA2)
                        currentOffsetSelected = cell3YOffset
                    }
                    .onGloballyPositioned {
                        cell3YOffset = it.positionInParent().x.toDp(context).dp
                    }
                    .padding(12.dp, 16.dp),
                textAlign = TextAlign.Center,
                maxLines = 1,
                style = MaterialTheme.typography.body3,
                text = stringResource(id = R.string.create_qr_label_wifi_encryption_wpa_wpa2),
                color = animateColorAsState(
                    if (currentOffsetSelected == cell3YOffset) {
                        colorResource(id = R.color.sc_white)
                    } else colorResource(id = R.color.sc_black)
                ).value
            )
        }
    }
}