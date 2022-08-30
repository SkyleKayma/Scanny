package fr.skyle.scanny.ui.createQRUrlScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
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
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import fr.skyle.scanny.R
import fr.skyle.scanny.enums.QRType
import fr.skyle.scanny.ext.QRCodeContent
import fr.skyle.scanny.ext.textId
import fr.skyle.scanny.theme.ScannyTheme
import fr.skyle.scanny.ui.core.CreateQRScaffold
import fr.skyle.scanny.ui.core.ScannyTextField
import fr.skyle.scanny.ui.generateQR.components.QRTypeSquareCell
import kotlinx.coroutines.launch

val presets = listOf("http://", "https://", "www.", ".com")

@Composable
fun CreateQRUrlScreen(
    goBackToQRGenerator: () -> Boolean,
    goToCustomQRCode: (QRCodeContent) -> Unit
) {
    val context = LocalContext.current

    val focusRequester = remember { FocusRequester() }
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()
    val bringIntoViewRequester = remember { BringIntoViewRequester() }

    var url by remember { mutableStateOf("") }

    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }

    CreateQRScaffold(
        scaffoldState = scaffoldState,
        title = stringResource(id = QRType.URL.textId),
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
                    .padding(horizontal = 24.dp)
                    .height(IntrinsicSize.Min),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                QRTypeSquareCell(QRType.URL)

                Spacer(modifier = Modifier.height(16.dp))

                ScannyTextField(
                    label = "",
                    keyboardType = KeyboardType.Text,
                    maxLines = 1,
                    scope = scope,
                    value = url,
                    onValueChange = {
                        url = it
                    },
                    bringIntoViewRequester = bringIntoViewRequester,
                    trailingIconId = R.drawable.ic_close,
                    modifier = Modifier
                        .focusRequester(focusRequester),
                )

                Spacer(modifier = Modifier.height(16.dp))

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    mainAxisSpacing = 8.dp,
                    mainAxisAlignment = FlowMainAxisAlignment.Start
                ) {
                    presets.forEach {
                        Button(
                            shape = RoundedCornerShape(12.dp),
                            onClick = {
                                url += it
                            }
                        ) {
                            Text(text = it)
                        }
                    }
                }

                Spacer(
                    modifier = Modifier
                        .weight(1f)
                        .heightIn(16.dp)
                )

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    onClick = {
                        scope.launch {
                            if (isContentValid(url)) {
                                goToCustomQRCode(QRCodeContent.QRCodeUrlContent(url))
                            } else scaffoldState.snackbarHostState.showSnackbar(context.getString(R.string.generic_please_fill_mandatory_fields))
                        }
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(12.dp, 6.dp),
                        text = stringResource(id = R.string.generic_create)
                    )
                }
            }
        }
    }
}

fun isContentValid(url: String): Boolean =
    url.isNotBlank()

@Preview
@Composable
fun PreviewCreateQRUrlScreen() {
    ScannyTheme {
        CreateQRUrlScreen(
            goBackToQRGenerator = { true },
            goToCustomQRCode = { QRCodeContent.QRCodeTextContent("Text") }
        )
    }
}