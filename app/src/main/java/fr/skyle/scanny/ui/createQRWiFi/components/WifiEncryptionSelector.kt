package fr.skyle.scanny.ui.createQRWiFi.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import fr.skyle.scanny.ANIMATION_TIME_TRANSITION
import fr.skyle.scanny.R
import fr.skyle.scanny.enums.WifiEncryptionType
import fr.skyle.scanny.ext.toDp
import fr.skyle.scanny.theme.SCAppTheme

@Composable
fun WifiEncryptionSelector(
    encryptionType: WifiEncryptionType,
    modifier: Modifier = Modifier,
    onEncryptionTypeChanged: (WifiEncryptionType) -> Unit
) {
    // Context
    val context = LocalContext.current

    // Remember
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
                .clip(RoundedCornerShape(10.dp))
                .background(SCAppTheme.colors.nuance100)
                .size(LocalDensity.current.run { sizeState.width.toDp() }, LocalDensity.current.run { sizeState.height.toDp() })
                .align(Alignment.CenterStart)
        ) {}

        Row(
            modifier = modifier
                .border(1.dp, SCAppTheme.colors.nuance10, RoundedCornerShape(10.dp))
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(10.dp))
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
                style = SCAppTheme.typography.body3,
                text = stringResource(id = R.string.create_qr_label_wifi_encryption_none),
                color = animateColorAsState(
                    if (currentOffsetSelected == cell1YOffset) {
                        SCAppTheme.colors.nuance100
                    } else SCAppTheme.colors.nuance10,
                    label = ""
                ).value
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(10.dp))
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
                style = SCAppTheme.typography.body3,
                text = stringResource(id = R.string.create_qr_label_wifi_encryption_wep),
                color = animateColorAsState(
                    if (currentOffsetSelected == cell2YOffset) {
                        SCAppTheme.colors.nuance100
                    } else SCAppTheme.colors.nuance10
                ).value
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(10.dp))
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
                style = SCAppTheme.typography.body3,
                text = stringResource(id = R.string.create_qr_label_wifi_encryption_wpa_wpa2),
                color = animateColorAsState(
                    if (currentOffsetSelected == cell3YOffset) {
                        SCAppTheme.colors.nuance100
                    } else SCAppTheme.colors.nuance10
                ).value
            )
        }
    }
}