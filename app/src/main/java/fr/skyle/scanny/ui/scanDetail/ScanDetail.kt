package fr.skyle.scanny.ui.scanDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.mlkit.vision.barcode.common.Barcode
import fr.skyle.scanny.ext.toQRCodeContent
import fr.skyle.scanny.theme.SCAppTheme
import fr.skyle.scanny.theme.SCTheme
import fr.skyle.scanny.ui.scanDetail.components.QRContentScanDisplay
import fr.skyle.scanny.utils.qrCode.QRCodeContent

@Composable
fun ScanDetail(
    barcode: Barcode? = null,
    onCopyContent: (AnnotatedString) -> Unit,
    onShareContent: (String) -> Unit,
    onOpenLink: (QRCodeContent.UrlContent) -> Unit,
    onSendEmail: (QRCodeContent.EmailMessageContent) -> Unit,
    onSendSMS: (QRCodeContent.SMSContent) -> Unit,
    onConnectToWifi: (QRCodeContent.WiFiContent) -> Unit,
    onAddToContact: (QRCodeContent.ContactContent) -> Unit,
    isRawContentShown: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                .background(SCAppTheme.colors.background)
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            QRContentScanDisplay(
                qrCodeContent = barcode.toQRCodeContent,
                onCopyContent = onCopyContent,
                onShareContent = onShareContent,
                onOpenLink = onOpenLink,
                onSendEmail = onSendEmail,
                onSendSMS = onSendSMS,
                onConnectToWifi = onConnectToWifi,
                onAddToContact = onAddToContact,
                isRawContentShown = isRawContentShown
            )
        }
    }
}

@Preview
@Composable
fun PreviewScanSuccessBottomSheet() {
    SCTheme {
        ScanDetail(
            barcode = null,
            onCopyContent = {},
            onShareContent = {},
            onOpenLink = {},
            onSendEmail = {},
            onSendSMS = {},
            onConnectToWifi = {},
            onAddToContact = {},
            isRawContentShown = false
        )
    }
}