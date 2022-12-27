package fr.skyle.scanny.ui.createQRSMS

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
import fr.skyle.scanny.R
import fr.skyle.scanny.enums.QRType
import fr.skyle.scanny.ui.core.buttons.SCButton
import fr.skyle.scanny.ui.core.textFields.ScannyTextField
import fr.skyle.scanny.ui.generateQR.components.QRTypeSquareCell
import fr.skyle.scanny.utils.qrCode.QRCodeContent
import kotlinx.coroutines.launch


@Composable
fun CreateQRSMSScreen(
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
    var phoneNumberState by remember { mutableStateOf(TextFieldValue("")) }
    var messageState by remember { mutableStateOf(TextFieldValue("")) }

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
        QRTypeSquareCell(QRType.SMS)

        Spacer(modifier = Modifier.height(16.dp))

        ScannyTextField(
            modifier = Modifier.focusRequester(focusRequester),
            label = stringResource(id = R.string.create_qr_label_sms_phone_number),
            keyboardType = KeyboardType.Phone,
            bringIntoViewRequester = bringIntoViewRequester,
            scope = scope,
            value = phoneNumberState,
            onValueChange = {
                phoneNumberState = it
            },
            imeAction = ImeAction.Next,
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(8.dp))

        ScannyTextField(
            label = stringResource(id = R.string.create_qr_label_sms_message),
            keyboardType = KeyboardType.Text,
            bringIntoViewRequester = bringIntoViewRequester,
            scope = scope,
            value = messageState,
            onValueChange = {
                messageState = it
            },
            maxLines = 1,
            capitalization = KeyboardCapitalization.Sentences
        )

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
                    if (isContentValid(phoneNumberState.text, messageState.text)) {
                        goToGenerateQRCode(QRCodeContent.SMSContent(phoneNumberState.text, messageState.text))
                    } else scaffoldState.snackbarHostState.showSnackbar(context.getString(R.string.generic_please_fill_mandatory_fields))
                }
            }
        )
    }
}

private fun isContentValid(phoneNumber: String, message: String): Boolean =
    phoneNumber.isNotBlank() && message.isNotBlank()