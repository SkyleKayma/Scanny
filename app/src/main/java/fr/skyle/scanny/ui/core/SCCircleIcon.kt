package fr.skyle.scanny.ui.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.skyle.scanny.enums.BarcodeFormat
import fr.skyle.scanny.ext.iconId
import fr.skyle.scanny.theme.SCAppTheme
import fr.skyle.scanny.theme.SCTheme
import fr.skyle.scanny.utils.qrCode.QRCodeContent

@Composable
fun SCCircleIcon(
    qrCodeContent: QRCodeContent
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(100))
            .background(SCAppTheme.colors.backgroundLight)
            .padding(16.dp)
    ) {
        Icon(
            modifier = Modifier.size(28.dp),
            painter = painterResource(id = qrCodeContent.type.iconId),
            contentDescription = "",
            tint = SCAppTheme.colors.primary
        )
    }
}

@Preview
@Composable
fun PreviewSCCircleIcon() {
    SCTheme {
        SCCircleIcon(
            qrCodeContent = QRCodeContent.WiFiContent(format = BarcodeFormat.QR_CODE, rawData = null)
        )
    }
}