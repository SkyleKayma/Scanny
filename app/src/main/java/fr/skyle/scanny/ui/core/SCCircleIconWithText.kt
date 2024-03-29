package fr.skyle.scanny.ui.core

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.skyle.scanny.enums.BarcodeFormat
import fr.skyle.scanny.ext.textId
import fr.skyle.scanny.theme.SCAppTheme
import fr.skyle.scanny.theme.SCTheme
import fr.skyle.scanny.utils.qrCode.QRCodeContent

@Composable
fun SCCircleIconWithText(
    qrCodeContent: QRCodeContent
) {
    Column(
        modifier = Modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SCCircleIcon(qrCodeContent = qrCodeContent)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(id = qrCodeContent.type.textId),
            color = SCAppTheme.colors.textPrimary,
        )
    }
}

@Preview
@Composable
fun PreviewSCCircleIconWithText() {
    SCTheme {
        SCCircleIconWithText(qrCodeContent = QRCodeContent.WiFiContent(format = BarcodeFormat.QR_CODE, rawData = null))
    }
}