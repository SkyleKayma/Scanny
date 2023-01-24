package fr.skyle.scanny.ui.createQRWiFi

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import fr.skyle.scanny.ANIMATION_TIME_TRANSITION
import fr.skyle.scanny.R
import fr.skyle.scanny.enums.QRType
import fr.skyle.scanny.enums.WifiEncryptionType
import fr.skyle.scanny.ui.core.buttons.SCButton
import fr.skyle.scanny.ui.core.textFields.ScannyPasswordTextField
import fr.skyle.scanny.ui.core.textFields.ScannyCleanableTextField
import fr.skyle.scanny.ui.createQRWiFi.components.WifiEncryptionSelector
import fr.skyle.scanny.ui.generateQR.components.QRTypeSquareCell
import fr.skyle.scanny.utils.qrCode.QRCodeContent
import kotlinx.coroutines.launch


@Composable
fun CreateQRWiFiScreen(
    scaffoldState: ScaffoldState,
    focusRequester: FocusRequester,
    bringIntoViewRequester: BringIntoViewRequester,
    goToGenerateQRCode: (QRCodeContent) -> Unit
) {
    // Context
    val context = LocalContext.current

    // Remember
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    var ssidState by remember { mutableStateOf(TextFieldValue("")) }
    var encryptionType by remember { mutableStateOf(WifiEncryptionType.NONE) }
    var passwordState by remember { mutableStateOf(TextFieldValue("")) }
    val passwordVisibility by remember(encryptionType) { mutableStateOf(encryptionType != WifiEncryptionType.NONE) }

    // Effect
    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .height(IntrinsicSize.Max)
            .padding(24.dp, 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        QRTypeSquareCell(QRType.WIFI)

        Spacer(modifier = Modifier.height(16.dp))

        ScannyCleanableTextField(
            modifier = Modifier.focusRequester(focusRequester),
            label = stringResource(id = R.string.create_qr_label_wifi_ssid),
            keyboardType = KeyboardType.Text,
            bringIntoViewRequester = bringIntoViewRequester,
            value = ssidState,
            onValueChange = {
                ssidState = it
            },
            imeAction = ImeAction.Next,
            maxLines = 1,
            capitalization = KeyboardCapitalization.Sentences
        )

        Spacer(modifier = Modifier.height(16.dp))

        WifiEncryptionSelector(
            encryptionType = encryptionType,
        ) {
            encryptionType = it
        }

        AnimatedVisibility(
            visible = passwordVisibility,
            enter = fadeIn(),
            exit = fadeOut(animationSpec = tween(durationMillis = ANIMATION_TIME_TRANSITION))
        ) {
            Column {
                Spacer(modifier = Modifier.height(8.dp))

                ScannyPasswordTextField(
                    label = stringResource(id = R.string.create_qr_label_wifi_password),
                    bringIntoViewRequester = bringIntoViewRequester,
                    value = passwordState,
                    onValueChange = {
                        passwordState = it
                    },
                    maxLines = 1,
                    capitalization = KeyboardCapitalization.None,
                    imeAction = ImeAction.Done
                )
            }
        }

        Spacer(
            modifier = Modifier
                .weight(1f)
                .heightIn(16.dp)
        )

        SCButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.generic_create),
            onClick = {
                scope.launch {
                    if (isContentValid(ssidState.text, passwordState.text)) {
                        goToGenerateQRCode(QRCodeContent.WiFiContent(ssidState.text, encryptionType, passwordState.text))
                    } else scaffoldState.snackbarHostState.showSnackbar(context.getString(R.string.generic_please_fill_mandatory_fields))
                }
            }
        )
    }
}

private fun isContentValid(ssid: String, password: String): Boolean =
    ssid.isNotBlank() && password.isNotBlank()