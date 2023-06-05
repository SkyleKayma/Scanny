package fr.skyle.scanny.ui.createQRText

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import fr.skyle.scanny.data.enums.BarcodeFormat
import fr.skyle.scanny.data.enums.BarcodeType
import fr.skyle.scanny.ui.core.buttons.SCButton
import fr.skyle.scanny.ui.core.textFields.ScannyCleanableTextField
import fr.skyle.scanny.ui.generateQR.components.QRTypeSquareCell
import fr.skyle.scanny.enums.BarcodeCodeContent
import kotlinx.coroutines.launch


@Composable
fun CreateQRTextScreen(
    scaffoldState: ScaffoldState,
    focusRequester: FocusRequester,
    bringIntoViewRequester: BringIntoViewRequester,
    goToGenerateQRCode: (BarcodeCodeContent) -> Unit,
) {
    // Context
    val context = LocalContext.current

    // Remember
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    var textState by remember { mutableStateOf(TextFieldValue("")) }

    // Effect
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
        QRTypeSquareCell(BarcodeType.TEXT)

        Spacer(modifier = Modifier.height(16.dp))

        ScannyCleanableTextField(
            modifier = Modifier
                .focusRequester(focusRequester)
                .height(200.dp),
            label = stringResource(id = R.string.create_qr_label_text),
            keyboardType = KeyboardType.Text,
            bringIntoViewRequester = bringIntoViewRequester,
            value = textState,
            onValueChange = {
                textState = it
            },
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Default
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
                    if (isContentValid(textState.text)) {
                        goToGenerateQRCode(BarcodeCodeContent.TextContent(text = textState.text, format = BarcodeFormat.QR_CODE, rawData = null))
                    } else scaffoldState.snackbarHostState.showSnackbar(context.getString(R.string.generic_please_fill_mandatory_fields))
                }
            }
        )
    }
}

private fun isContentValid(text: String): Boolean =
    text.isNotBlank()