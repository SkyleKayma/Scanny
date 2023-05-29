package fr.skyle.scanny.ui.scanDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
    onAddToContact: (QRCodeContent.ContactContent) -> Unit,
    isRawContentShown: () -> Boolean
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
                .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                .background(SCAppTheme.colors.background)
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            QRContentScanDisplay(
                qrCodeContent = barcode.toQRCodeContent,
                onCopyContent = onCopyContent,
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
            onAddToContact = {},
            isRawContentShown = { false }
        )
    }
}