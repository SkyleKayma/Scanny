package fr.skyle.scanny.ui.createQR.screens

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
import fr.skyle.scanny.ui.core.ScannyButton
import fr.skyle.scanny.ui.core.ScannyTextField
import fr.skyle.scanny.ui.generateQR.components.QRTypeSquareCell
import fr.skyle.scanny.utils.qrCode.QRCodeContent
import kotlinx.coroutines.launch


@Composable
fun CreateQRTextScreen(
    scaffoldState: ScaffoldState,
    focusRequester: FocusRequester,
    bringIntoViewRequester: BringIntoViewRequester,
    goToGenerateQRCode: (QRCodeContent) -> Unit,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    var textState by remember { mutableStateOf(TextFieldValue("")) }

    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .height(IntrinsicSize.Max)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        QRTypeSquareCell(QRType.TEXT)

        Spacer(modifier = Modifier.height(16.dp))

        ScannyTextField(
            label = stringResource(id = R.string.create_qr_label_text),
            keyboardType = KeyboardType.Text,
            bringIntoViewRequester = bringIntoViewRequester,
            scope = scope,
            value = textState,
            onValueChange = {
                textState = it
            },
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Default,
            trailingIconId = R.drawable.ic_close,
            modifier = Modifier
                .focusRequester(focusRequester)
                .height(200.dp)
        )

        Spacer(
            modifier = Modifier
                .weight(1f)
                .heightIn(16.dp)
        )

        ScannyButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.generic_create),
            onClick = {
                scope.launch {
                    if (isContentValid(textState.text)) {
                        goToGenerateQRCode(QRCodeContent.TextContent(textState.text))
                    } else scaffoldState.snackbarHostState.showSnackbar(context.getString(R.string.generic_please_fill_mandatory_fields))
                }
            }
        )
    }
}

private fun isContentValid(text: String): Boolean =
    text.isNotBlank()