package fr.skyle.scanny.ext

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import fr.skyle.scanny.R
import fr.skyle.scanny.enums.WifiEncryptionType
import fr.skyle.scanny.theme.SCAppTheme
import fr.skyle.scanny.utils.qrCode.QRCodeContent

fun QRCodeContent.asEncodedString(): String =
    when (this) {
        is QRCodeContent.TextContent ->
            text

        // http://test.com
        is QRCodeContent.UrlContent ->
            url

        // SMSTO:+33122334455:Message
        is QRCodeContent.SMSContent ->
            mutableListOf<String>().apply {
                // Begin (mandatory)
                add(QRCodeContent.SMSContent.SMS_TO)

                // Tel (optional)
                add(phoneNumber ?: "")

                // Message (optional)
                add(message ?: "")
            }.joinToString(":")

        // MAILTO:test@test.com
        is QRCodeContent.EmailContent ->
            mutableListOf<String>().apply {
                add(QRCodeContent.EmailContent.MAILTO)
                add(email)
            }.joinToString(":")

        // MATMSG:TO:mail@test.com;SUB:Subject;BODY:Message;;
        is QRCodeContent.EmailMessageContent ->
            buildString {
                // Prefix
                append("${QRCodeContent.EmailMessageContent.BEGIN}:")

                // Email
                email?.let {
                    append("${QRCodeContent.EmailMessageContent.TO}:")
                    append("$it;")
                }

                // Subject
                subject?.let {
                    append("${QRCodeContent.EmailMessageContent.SUBJECT}:")
                    append("$it;")
                }

                // Body
                body?.let {
                    append("${QRCodeContent.EmailMessageContent.BODY}:")
                    append("$it;")
                }

                // End
                append(QRCodeContent.EmailMessageContent.END)
            }

        // WIFI:S:NEUF_0809;T:WEP;P:Password;H:true
        is QRCodeContent.WiFiContent ->
            buildString {
                append("${QRCodeContent.WiFiContent.WIFI}:")

                // SSID
                ssid?.let {
                    append("${QRCodeContent.WiFiContent.SSID}:")
                    append("$it;")
                }

                encryptionType?.let { type ->
                    // Encryption type
                    append("${QRCodeContent.WiFiContent.TYPE}:")
                    append("${type.name};")

                    // Password
                    password?.let {
                        if (type != WifiEncryptionType.NONE) {
                            append("${QRCodeContent.WiFiContent.PASSWORD}:")
                            append("$it;")
                        }
                    }
                }

                // Hidden
                if (isHidden == true) {
                    append("${QRCodeContent.WiFiContent.HIDDEN}:")
                    append("${isHidden.toString()};")
                }

                append(QRCodeContent.WiFiContent.END)
            }

        // TODO
        is QRCodeContent.ContactContent ->
            buildString {
                // Begin
                appendLine(QRCodeContent.ContactContent.BEGIN)

                // Names
                if (firstName != null || lastName != null) {
                    append(QRCodeContent.ContactContent.NAMES)
                    append(":")

                    // First name
                    append(firstName)
                    append(";")

                    // Last name
                    append(lastName)
                    append(";")
                    appendLine()
                }

                // End
                appendLine(QRCodeContent.ContactContent.VERSION)
                appendLine(QRCodeContent.ContactContent.END)
            }
    }

@Composable
fun QRCodeContent.asFormattedString(context: Context): AnnotatedString? {
    val spanStyle =
        SpanStyle(
            color = SCAppTheme.colors.textPrimary,
            fontWeight = FontWeight.Bold,
            fontStyle = SCAppTheme.typography.body1.fontStyle
        )

    return when (this) {
        is QRCodeContent.TextContent ->
            null
        is QRCodeContent.UrlContent ->
            null
        is QRCodeContent.SMSContent ->
            buildAnnotatedString {
                append(context.getString(R.string.qr_format_sms_phone_number))
                append(" ")
                withStyle(style = spanStyle) {
                    append(phoneNumber ?: "-")
                }

                appendLine()

                append(context.getString(R.string.qr_format_sms_message))
                append(" ")
                withStyle(style = spanStyle) {
                    append(message ?: "-")
                }
            }
        is QRCodeContent.EmailContent ->
            buildAnnotatedString {
                append(context.getString(R.string.qr_format_email_to))
                append(" ")
                withStyle(style = spanStyle) {
                    append(email)
                }
            }
        is QRCodeContent.EmailMessageContent ->
            buildAnnotatedString {
                append(context.getString(R.string.qr_format_email_to))
                append(" ")
                withStyle(style = spanStyle) {
                    append(email ?: "")
                }

                appendLine()

                append(context.getString(R.string.qr_format_email_subject))
                append(" ")
                withStyle(style = spanStyle) {
                    append(subject ?: "-")
                }

                appendLine()

                append(context.getString(R.string.qr_format_email_mail_content))
                append(" ")
                withStyle(style = spanStyle) {
                    append(body ?: "-")
                }
            }
        is QRCodeContent.WiFiContent ->
            buildAnnotatedString {
                append(context.getString(R.string.qr_format_wifi_ssid))
                append(" ")
                withStyle(style = spanStyle) {
                    append(ssid ?: "-")
                }

                appendLine()

                append(context.getString(R.string.qr_format_wifi_encryption))
                append(" ")
                withStyle(style = spanStyle) {
                    append(encryptionType?.name ?: "-")
                }

                appendLine()

                append(context.getString(R.string.qr_format_wifi_password))
                append(" ")
                withStyle(style = spanStyle) {
                    append(password ?: "-")
                }

                appendLine()

                append(context.getString(R.string.qr_format_wifi_is_hidden))
                append(" ")
                withStyle(style = spanStyle) {
                    append(if (isHidden == true) context.getString(R.string.generic_yes) else context.getString(R.string.generic_no))
                }
            }
        is QRCodeContent.ContactContent ->
            buildAnnotatedString {
                append("TODO")
            }
    }
}