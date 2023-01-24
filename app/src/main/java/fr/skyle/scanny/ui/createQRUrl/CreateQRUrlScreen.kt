package fr.skyle.scanny.ui.createQRUrl

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
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import fr.skyle.scanny.R
import fr.skyle.scanny.enums.QRType
import fr.skyle.scanny.ui.core.buttons.SCButton
import fr.skyle.scanny.ui.core.buttons.SCButtonSelector
import fr.skyle.scanny.ui.core.textFields.ScannyCleanableTextField
import fr.skyle.scanny.ui.generateQR.components.QRTypeSquareCell
import fr.skyle.scanny.utils.qrCode.QRCodeContent
import kotlinx.coroutines.launch

val presets = listOf("https://", "http://", "www.", ".com")

@Composable
fun CreateQRUrlScreen(
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
    var textState by remember { mutableStateOf(TextFieldValue(presets.first(), TextRange(presets.first().length))) }

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
        QRTypeSquareCell(QRType.URL)

        Spacer(modifier = Modifier.height(16.dp))

        ScannyCleanableTextField(
            modifier = Modifier.focusRequester(focusRequester),
            label = stringResource(id = R.string.create_qr_label_link),
            keyboardType = KeyboardType.Text,
            bringIntoViewRequester = bringIntoViewRequester,
            value = textState,
            onValueChange = {
                textState = it
            },
            maxLines = 1,
            imeAction = ImeAction.Done,
            autoCorrect = false
        )

        Spacer(modifier = Modifier.height(16.dp))

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            mainAxisSpacing = 8.dp,
            mainAxisAlignment = FlowMainAxisAlignment.Start
        ) {
            presets.forEach { preset ->
                SCButtonSelector(
                    text = preset,
                    onClick = {
                        val currentIndex = textState.selection.start
                        textState = textState.copy(
                            text = textState.text.substring(0 until currentIndex) + preset + textState.text.substring(
                                currentIndex,
                                textState.text.length
                            ),
                            TextRange(currentIndex + preset.length)
                        )
                    }
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
                    if (isContentValid(textState.text)) {
                        goToGenerateQRCode(QRCodeContent.UrlContent(textState.text))
                    } else scaffoldState.snackbarHostState.showSnackbar(context.getString(R.string.generic_please_fill_mandatory_fields))
                }
            }
        )
    }
}

private fun isContentValid(url: String): Boolean =
    url.isNotBlank()