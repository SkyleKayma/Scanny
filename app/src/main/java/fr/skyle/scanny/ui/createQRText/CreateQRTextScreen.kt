package fr.skyle.scanny.ui.createQRText

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.skyle.scanny.R
import fr.skyle.scanny.enums.QRType
import fr.skyle.scanny.ext.QRCodeContent
import fr.skyle.scanny.ext.textId
import fr.skyle.scanny.theme.ScannyTheme
import fr.skyle.scanny.ui.core.CreateQRScaffold
import fr.skyle.scanny.ui.core.ScannyButton
import fr.skyle.scanny.ui.core.ScannyTextField
import fr.skyle.scanny.ui.generateQR.components.QRTypeSquareCell
import kotlinx.coroutines.launch


@Composable
fun CreateQRTextScreen(
    goBackToQRGenerator: () -> Boolean,
    goToCustomQRCode: (QRCodeContent) -> Unit
) {
    val context = LocalContext.current

    val focusRequester = remember { FocusRequester() }
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()
    val bringIntoViewRequester = remember { BringIntoViewRequester() }

    var text by remember { mutableStateOf("") }

    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }

    CreateQRScaffold(
        scaffoldState = scaffoldState,
        title = stringResource(id = QRType.TEXT.textId),
        onClickHomeButton = goBackToQRGenerator,
        modifier = Modifier
            .imePadding()
            .bringIntoViewRequester(bringIntoViewRequester)
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
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
                    label = "",
                    keyboardType = KeyboardType.Text,
                    bringIntoViewRequester = bringIntoViewRequester,
                    scope = scope,
                    value = text,
                    onValueChange = {
                        text = it
                    },
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
                            if (isContentValid(text)) {
                                goToCustomQRCode(QRCodeContent.QRCodeTextContent(text))
                            } else scaffoldState.snackbarHostState.showSnackbar(context.getString(R.string.generic_please_fill_mandatory_fields))
                        }
                    }
                )
            }
        }
    }
}

fun isContentValid(text: String): Boolean =
    text.isNotBlank()

@Preview
@Composable
fun PreviewCreateQRUrlScreen() {
    ScannyTheme {
        CreateQRTextScreen(
            goBackToQRGenerator = { true },
            goToCustomQRCode = { QRCodeContent.QRCodeTextContent("Text") }
        )
    }
}