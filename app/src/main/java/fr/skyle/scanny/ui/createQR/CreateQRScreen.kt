package fr.skyle.scanny.ui.createQR

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import fr.skyle.scanny.enums.BarcodeFormat
import fr.skyle.scanny.enums.QRType
import fr.skyle.scanny.ext.textId
import fr.skyle.scanny.theme.SCTheme
import fr.skyle.scanny.ui.createQR.components.CreateQRScaffold
import fr.skyle.scanny.ui.createQREmail.CreateQREmailScreen
import fr.skyle.scanny.ui.createQRSMS.CreateQRSMSScreen
import fr.skyle.scanny.ui.createQRText.CreateQRTextScreen
import fr.skyle.scanny.ui.createQRUrl.CreateQRUrlScreen
import fr.skyle.scanny.ui.createQRWiFi.CreateQRWiFiScreen
import fr.skyle.scanny.utils.qrCode.QRCodeContent


@Composable
fun CreateQRScreen(
    qrType: QRType,
    goBackToGenerateQRList: () -> Boolean,
    goToGenerateQRCode: (QRCodeContent) -> Unit
) {
    // Remember
    val focusRequester = remember { FocusRequester() }
    val scaffoldState = rememberScaffoldState()
    val bringIntoViewRequester = remember { BringIntoViewRequester() }

    // Effect
    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }

    CreateQRScaffold(
        modifier = Modifier
            .systemBarsPadding()
            .imePadding()
            .bringIntoViewRequester(bringIntoViewRequester),
        scaffoldState = scaffoldState,
        title = stringResource(id = qrType.textId),
        onClickHomeButton = goBackToGenerateQRList
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (qrType) {
                QRType.TEXT ->
                    CreateQRTextScreen(
                        scaffoldState = scaffoldState,
                        focusRequester = focusRequester,
                        bringIntoViewRequester = bringIntoViewRequester,
                        goToGenerateQRCode = goToGenerateQRCode
                    )
                QRType.URL ->
                    CreateQRUrlScreen(
                        scaffoldState = scaffoldState,
                        focusRequester = focusRequester,
                        bringIntoViewRequester = bringIntoViewRequester,
                        goToGenerateQRCode = goToGenerateQRCode
                    )
                QRType.EMAIL, QRType.EMAIL_MSG ->
                    CreateQREmailScreen(
                        scaffoldState = scaffoldState,
                        focusRequester = focusRequester,
                        bringIntoViewRequester = bringIntoViewRequester,
                        goToGenerateQRCode = goToGenerateQRCode
                    )
                QRType.SMS ->
                    CreateQRSMSScreen(
                        scaffoldState = scaffoldState,
                        focusRequester = focusRequester,
                        bringIntoViewRequester = bringIntoViewRequester,
                        goToGenerateQRCode = goToGenerateQRCode
                    )
                QRType.WIFI ->
                    CreateQRWiFiScreen(
                        scaffoldState = scaffoldState,
                        focusRequester = focusRequester,
                        bringIntoViewRequester = bringIntoViewRequester,
                        goToGenerateQRCode = goToGenerateQRCode
                    )
                QRType.CONTACT -> TODO()
            }
        }
    }
}

@Preview
@Composable
fun PreviewCreateQRTextScreen() {
    SCTheme {
        CreateQRScreen(
            qrType = QRType.TEXT,
            goBackToGenerateQRList = { true },
            goToGenerateQRCode = { QRCodeContent.TextContent(text = "Text", format = BarcodeFormat.QR_CODE, rawData = null) }
        )
    }
}

@Preview
@Composable
fun PreviewCreateQRUrlScreen() {
    SCTheme {
        CreateQRScreen(
            qrType = QRType.URL,
            goBackToGenerateQRList = { true },
            goToGenerateQRCode = { QRCodeContent.TextContent(text = "Url", format = BarcodeFormat.QR_CODE, rawData = null) }
        )
    }
}

@Preview
@Composable
fun PreviewCreateQREmailScreen() {
    SCTheme {
        CreateQRScreen(
            qrType = QRType.EMAIL,
            goBackToGenerateQRList = { true },
            goToGenerateQRCode = { QRCodeContent.TextContent(text = "Email", format = BarcodeFormat.QR_CODE, rawData = null) }
        )
    }
}