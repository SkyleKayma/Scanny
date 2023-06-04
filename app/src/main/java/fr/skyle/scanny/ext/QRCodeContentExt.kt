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

                append(QRCodeContent.WiFiContent.END)
            }

        is QRCodeContent.ContactContent ->
            buildString {
                // Begin
                appendLine(QRCodeContent.ContactContent.BEGIN)

                // Version
                appendLine(QRCodeContent.ContactContent.VERSION)

                // Names
                names?.let {
                    appendLine(QRCodeContent.ContactContent.NAMES)

                    it.forEachIndexed { index, s ->
                        if (index != 0) {
                            append(";")
                        }

                        append(it)
                    }
                }

                // Formatted name
                formattedName?.let {
                    appendLine(QRCodeContent.ContactContent.FORMATTED_NAME)
                    append(it)
                }

                // Org
                org?.let {
                    appendLine(QRCodeContent.ContactContent.ORG)
                    append(it)
                }

                // Title
                title?.let {
                    appendLine(QRCodeContent.ContactContent.TITLE)
                    append(it)
                }

                // Tel
                tels?.let {
                    it.firstOrNull()?.let { firstTel ->
                        appendLine(QRCodeContent.ContactContent.TEL)
                        append(firstTel)
                    }
                }

                // Email
                emails?.let {
                    it.firstOrNull()?.let { firstEmail ->
                        appendLine(QRCodeContent.ContactContent.EMAIL)
                        append(firstEmail)
                    }
                }

                // Address
                addresses?.let {
                    it.firstOrNull()?.let { firstAddress ->
                        appendLine(QRCodeContent.ContactContent.ADDRESS)
                        append(firstAddress)
                    }
                }

                // URL
                urls?.let {
                    it.firstOrNull()?.let { firstUrl ->
                        appendLine(QRCodeContent.ContactContent.URL)
                        append(firstUrl)
                    }
                }

                // End
                appendLine(QRCodeContent.ContactContent.END)
            }
    }

@Composable
fun QRCodeContent.asFormattedString(context: Context): AnnotatedString? {
    val spanStyle =
        SpanStyle(
            color = SCAppTheme.colors.nuance10,
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
                val list = mutableListOf<AnnotatedString>()

                phoneNumber?.let {
                    list.add(
                        buildAnnotatedString {
                            append("${context.getString(R.string.qr_format_sms_phone_number)} ")
                            withStyle(style = spanStyle) {
                                append(it)
                            }
                        }
                    )
                }

                message?.let {
                    list.add(
                        buildAnnotatedString {
                            append("${context.getString(R.string.qr_format_sms_message)} ")
                            withStyle(style = spanStyle) {
                                append(it)
                            }
                        }
                    )
                }

                list.forEachIndexed { index, annotatedString ->
                    if (index != 0) {
                        appendLine()
                    }

                    append(annotatedString)
                }
            }

        is QRCodeContent.EmailContent ->
            buildAnnotatedString {
                append("${context.getString(R.string.qr_format_email_to)} ")
                withStyle(style = spanStyle) {
                    append(email)
                }
            }

        is QRCodeContent.EmailMessageContent ->
            buildAnnotatedString {
                val list = mutableListOf<AnnotatedString>()

                email?.let {
                    list.add(
                        buildAnnotatedString {
                            append("${context.getString(R.string.qr_format_email_to)} ")
                            withStyle(style = spanStyle) {
                                append(it)
                            }
                        }
                    )
                }

                subject?.let {
                    list.add(
                        buildAnnotatedString {
                            append("${context.getString(R.string.qr_format_email_subject)} ")
                            withStyle(style = spanStyle) {
                                append(it)
                            }
                        }
                    )
                }

                body?.let {
                    list.add(
                        buildAnnotatedString {
                            append("${context.getString(R.string.qr_format_email_mail_content)} ")
                            withStyle(style = spanStyle) {
                                append(it)
                            }
                        }
                    )
                }

                list.forEachIndexed { index, annotatedString ->
                    if (index != 0) {
                        appendLine()
                    }

                    append(annotatedString)
                }
            }

        is QRCodeContent.WiFiContent ->
            buildAnnotatedString {
                append("${context.getString(R.string.qr_format_wifi_ssid)} ")
                withStyle(style = spanStyle) {
                    appendLine(ssid ?: "-")
                }

                append("${context.getString(R.string.qr_format_wifi_encryption)} ")
                withStyle(style = spanStyle) {
                    appendLine(encryptionType?.name ?: "-")
                }

                append("${context.getString(R.string.qr_format_wifi_password)} ")
                withStyle(style = spanStyle) {
                    append(password ?: "-")
                }
            }

        is QRCodeContent.ContactContent ->
            buildAnnotatedString {
                val list = mutableListOf<AnnotatedString>()

                formattedName?.let {
                    list.add(
                        buildAnnotatedString {
                            append("Nom: ")
                            withStyle(style = spanStyle) {
                                append(it)
                            }
                        }
                    )
                }

                org?.let {
                    list.add(
                        buildAnnotatedString {
                            append("Société: ")
                            withStyle(style = spanStyle) {
                                append(it)
                            }
                        }
                    )
                }

                title?.let {
                    list.add(
                        buildAnnotatedString {
                            append("Titre: ")
                            withStyle(style = spanStyle) {
                                append(it)
                            }
                        }
                    )
                }

                tels?.let {
                    if (it.isNotEmpty()) {
                        list.add(
                            buildAnnotatedString {
                                val hasMoreThanOneItem = it.count() > 1

                                if (hasMoreThanOneItem) {
                                    appendLine()
                                }

                                append("Tel: ")

                                if (hasMoreThanOneItem) {
                                    appendLine()
                                }

                                withStyle(style = spanStyle) {
                                    it.forEachIndexed { index, tel ->
                                        if (index != 0) {
                                            appendLine()
                                        }

                                        append(tel)
                                    }
                                }
                            }
                        )
                    }
                }

                emails?.let {
                    if (it.isNotEmpty()) {
                        list.add(
                            buildAnnotatedString {
                                val hasMoreThanOneItem = it.count() > 1

                                if (hasMoreThanOneItem || (tels?.count() ?: 0) > 1) {
                                    appendLine()
                                }

                                append("Email: ")

                                if (hasMoreThanOneItem) {
                                    appendLine()
                                }

                                withStyle(style = spanStyle) {
                                    it.forEachIndexed { index, email ->
                                        if (index != 0) {
                                            appendLine()
                                        }

                                        append(email)
                                    }
                                }
                            }
                        )
                    }
                }

                addresses?.let {
                    if (it.isNotEmpty()) {
                        list.add(
                            buildAnnotatedString {
                                val hasMoreThanOneItem = it.count() > 1

                                if (hasMoreThanOneItem || (emails?.count() ?: 0) > 1) {
                                    appendLine()
                                }

                                append("Addresse: ")

                                if (hasMoreThanOneItem) {
                                    appendLine()
                                }

                                withStyle(style = spanStyle) {
                                    it.forEachIndexed { index, address ->
                                        if (index != 0) {
                                            appendLine()
                                        }

                                        append(address)
                                    }
                                }
                            }
                        )
                    }
                }

                urls?.let {
                    if (it.isNotEmpty()) {
                        list.add(
                            buildAnnotatedString {
                                val hasMoreThanOneItem = it.count() > 1

                                if (hasMoreThanOneItem || (addresses?.count() ?: 0) > 1) {
                                    appendLine()
                                }

                                append("Lien: ")

                                if (hasMoreThanOneItem) {
                                    appendLine()
                                }

                                withStyle(style = spanStyle) {
                                    it.forEachIndexed { index, url ->
                                        if (index != 0) {
                                            appendLine()
                                        }

                                        append(url)
                                    }
                                }
                            }
                        )
                    }
                }

                list.forEachIndexed { index, annotatedString ->
                    if (index != 0) {
                        appendLine()
                    }

                    append(annotatedString)
                }
            }
    }
}