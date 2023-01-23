package fr.skyle.scanny.ui.scanDetail.components

import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.skyle.scanny.R
import fr.skyle.scanny.enums.WifiEncryptionType
import fr.skyle.scanny.ext.asFormattedString
import fr.skyle.scanny.theme.SCTheme
import fr.skyle.scanny.ui.core.SCActionButton
import fr.skyle.scanny.ui.core.SCCircleIconWithText
import fr.skyle.scanny.utils.qrCode.QRCodeContent


@Composable
fun QRContentScanDisplay(
    qrCodeContent: QRCodeContent,
    onCopyContent: (AnnotatedString) -> Unit,
    onShareContent: (String) -> Unit,
    onOpenLink: (QRCodeContent.UrlContent) -> Unit,
    onSendEmail: (QRCodeContent.EmailMessageContent) -> Unit,
    onSendSMS: (QRCodeContent.SMSContent) -> Unit,
    onConnectToWifi: (QRCodeContent.WiFiContent) -> Unit,
    onAddToContact: (QRCodeContent.ContactContent) -> Unit,
    isRawContentShown: Boolean
) {
    // Context
    val context = LocalContext.current

    val formattedText = qrCodeContent.asFormattedString(context)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SCCircleIconWithText(qrCodeContent = qrCodeContent)

        Spacer(modifier = Modifier.height(24.dp))

        val formattedString = qrCodeContent.asFormattedString(context)

        formattedString?.let {
            QRContentDisplayHeaderSection(
                textId = if (isRawContentShown) {
                    R.string.scan_detail_formatted_content
                } else R.string.scan_detail_content,
                onShareClick = {
                    formattedText?.toString()?.let(onShareContent)
                },
                onCopyClick = {
                    formattedText?.let(onCopyContent)
                }
            )

            QRContentDisplayBodySection(text = formattedString)

            Spacer(modifier = Modifier.height(16.dp))
        }

        if ((isRawContentShown && formattedString != null) || formattedString == null) {
            QRContentDisplayHeaderSection(
                textId = if (formattedString != null) {
                    R.string.scan_detail_raw_content
                } else R.string.scan_detail_content,
                onShareClick = {
                    onShareContent(qrCodeContent.rawData ?: "")
                },
                onCopyClick = {
                    onCopyContent(
                        buildAnnotatedString {
                            append(qrCodeContent.rawData ?: "")
                        }
                    )
                }
            )

            QRContentDisplayBodySection(
                text = buildAnnotatedString {
                    append(qrCodeContent.rawData ?: "")
                }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            maxItemsInEachRow = 3
        ) {
            when (qrCodeContent) {
                is QRCodeContent.UrlContent ->
                    SCActionButton(
                        modifier = Modifier.weight(1f),
                        textId = R.string.scan_action_open,
                        iconId = R.drawable.ic_open_link,
                        onClick = {
                            onOpenLink(qrCodeContent)
                        }
                    )

                is QRCodeContent.EmailMessageContent ->
                    SCActionButton(
                        modifier = Modifier.weight(1f),
                        textId = R.string.scan_action_send_mail,
                        iconId = R.drawable.ic_action_send_mail,
                        onClick = {
                            onSendEmail(qrCodeContent)
                        }
                    )

                is QRCodeContent.SMSContent ->
                    SCActionButton(
                        modifier = Modifier.weight(1f),
                        textId = R.string.scan_action_send_sms,
                        iconId = R.drawable.ic_action_sms,
                        onClick = {
                            onSendSMS(qrCodeContent)
                        }
                    )

                is QRCodeContent.WiFiContent ->
                    SCActionButton(
                        modifier = Modifier.weight(1f),
                        textId = R.string.scan_action_connect_to_wifi,
                        iconId = R.drawable.ic_action_wifi,
                        onClick = {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                if (qrCodeContent.encryptionType == WifiEncryptionType.WEP) {
                                    // TODO Show popup to explain why WEP encryption is not supported
                                } else onConnectToWifi(qrCodeContent)
                            } else {
                                // TODO Show popup to explain how to add it manually
                            }
                        }
                    )

                is QRCodeContent.ContactContent ->
                    SCActionButton(
                        modifier = Modifier.weight(1f),
                        textId = R.string.scan_action_add_to_contacts,
                        iconId = R.drawable.ic_action_add_contact,
                        onClick = {
                            onAddToContact(qrCodeContent)
                        }
                    )

                else -> {}
            }
        }
    }
}

@Preview
@Composable
fun PreviewTextContentScanDisplay() {
    SCTheme {
        QRContentScanDisplay(
            qrCodeContent = QRCodeContent.TextContent(text = "Test"),
            onOpenLink = {},
            onSendSMS = {},
            onSendEmail = {},
            onConnectToWifi = {},
            onAddToContact = {},
            onCopyContent = {},
            onShareContent = {},
            isRawContentShown = false
        )
    }
}