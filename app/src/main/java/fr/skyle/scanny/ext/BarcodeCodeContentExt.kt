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
import fr.skyle.scanny.enums.BarcodeCodeContent

fun BarcodeCodeContent.asEncodedString(): String =
    when (this) {
        is BarcodeCodeContent.TextContent ->
            text ?: ""

        // http://test.com
        is BarcodeCodeContent.UrlContent ->
            url ?: ""

        // SMSTO:+33122334455:Message
        is BarcodeCodeContent.SMSContent ->
            mutableListOf<String>().apply {
                // Begin (mandatory)
                add(BarcodeCodeContent.SMSContent.SMS_TO)

                // Tel (optional)
                add(phoneNumber ?: "")

                // Message (optional)
                add(message ?: "")
            }.joinToString(":")

        // MAILTO:test@test.com
        is BarcodeCodeContent.EmailContent ->
            mutableListOf<String>().apply {
                add(BarcodeCodeContent.EmailContent.MAILTO)
                add(email ?: "")
            }.joinToString(":")

        // MATMSG:TO:mail@test.com;SUB:Subject;BODY:Message;;
        is BarcodeCodeContent.EmailMessageContent ->
            buildString {
                // Prefix
                append("${BarcodeCodeContent.EmailMessageContent.BEGIN}:")

                // Email
                email?.let {
                    append("${BarcodeCodeContent.EmailMessageContent.TO}:")
                    append("$it;")
                }

                // Subject
                subject?.let {
                    append("${BarcodeCodeContent.EmailMessageContent.SUBJECT}:")
                    append("$it;")
                }

                // Body
                body?.let {
                    append("${BarcodeCodeContent.EmailMessageContent.BODY}:")
                    append("$it;")
                }

                // End
                append(BarcodeCodeContent.EmailMessageContent.END)
            }

        // WIFI:S:NEUF_0809;T:WEP;P:Password;H:true
        is BarcodeCodeContent.WiFiContent ->
            buildString {
                append("${BarcodeCodeContent.WiFiContent.WIFI}:")

                // SSID
                ssid?.let {
                    append("${BarcodeCodeContent.WiFiContent.SSID}:")
                    append("$it;")
                }

                encryptionType?.let { type ->
                    // Encryption type
                    append("${BarcodeCodeContent.WiFiContent.TYPE}:")
                    append("${type.name};")

                    // Password
                    password?.let {
                        if (type != WifiEncryptionType.NONE) {
                            append("${BarcodeCodeContent.WiFiContent.PASSWORD}:")
                            append("$it;")
                        }
                    }
                }

                append(BarcodeCodeContent.WiFiContent.END)
            }

        is BarcodeCodeContent.ContactContent ->
            buildString {
                // Begin
                appendLine(BarcodeCodeContent.ContactContent.BEGIN)

                // Version
                appendLine(BarcodeCodeContent.ContactContent.VERSION)

                // Names
                if (names.isNotEmpty()) {
                    appendLine(BarcodeCodeContent.ContactContent.NAMES)

                    names.forEachIndexed { index, s ->
                        if (index != 0) {
                            append(";")
                        }

                        append(s)
                    }
                }

                // Formatted name
                formattedName?.let {
                    appendLine(BarcodeCodeContent.ContactContent.FORMATTED_NAME)
                    append(it)
                }

                // Org
                org?.let {
                    appendLine(BarcodeCodeContent.ContactContent.ORG)
                    append(it)
                }

                // Title
                title?.let {
                    appendLine(BarcodeCodeContent.ContactContent.TITLE)
                    append(it)
                }

                // Tel
                if (tels.isNotEmpty()) {
                    tels.firstOrNull()?.let { firstTel ->
                        appendLine(BarcodeCodeContent.ContactContent.TEL)
                        append(firstTel)
                    }
                }

                // Email
                if (emails.isNotEmpty()) {
                    emails.firstOrNull()?.let { firstEmail ->
                        appendLine(BarcodeCodeContent.ContactContent.EMAIL)
                        append(firstEmail)
                    }
                }

                // Address
                if (addresses.isNotEmpty()) {
                    addresses.firstOrNull()?.let { firstAddress ->
                        appendLine(BarcodeCodeContent.ContactContent.ADDRESS)
                        append(firstAddress)
                    }
                }

                // URL
                if (urls.isNotEmpty()) {
                    urls.firstOrNull()?.let { firstUrl ->
                        appendLine(BarcodeCodeContent.ContactContent.URL)
                        append(firstUrl)
                    }
                }

                // End
                appendLine(BarcodeCodeContent.ContactContent.END)
            }
    }

@Composable
fun BarcodeCodeContent.asFormattedString(context: Context): AnnotatedString? {
    val spanStyle =
        SpanStyle(
            color = SCAppTheme.colors.nuance10,
            fontWeight = FontWeight.Bold,
            fontStyle = SCAppTheme.typography.body1.fontStyle
        )

    return when (this) {
        is BarcodeCodeContent.TextContent ->
            null

        is BarcodeCodeContent.UrlContent ->
            null

        is BarcodeCodeContent.SMSContent ->
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

        is BarcodeCodeContent.EmailContent ->
            buildAnnotatedString {
                append("${context.getString(R.string.qr_format_email_to)} ")
                withStyle(style = spanStyle) {
                    append(email)
                }
            }

        is BarcodeCodeContent.EmailMessageContent ->
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

        is BarcodeCodeContent.WiFiContent ->
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

        is BarcodeCodeContent.ContactContent ->
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

                if (tels.isNotEmpty()) {
                    list.add(
                        buildAnnotatedString {
                            val hasMoreThanOneItem = tels.count() > 1

                            if (hasMoreThanOneItem) {
                                appendLine()
                            }

                            append("Tel: ")

                            if (hasMoreThanOneItem) {
                                appendLine()
                            }

                            withStyle(style = spanStyle) {
                                tels.forEachIndexed { index, tel ->
                                    if (index != 0) {
                                        appendLine()
                                    }

                                    append(tel)
                                }
                            }
                        }
                    )
                }

                if (emails.isNotEmpty()) {
                    list.add(
                        buildAnnotatedString {
                            val hasMoreThanOneItem = emails.count() > 1

                            if (hasMoreThanOneItem || tels.count() > 1) {
                                appendLine()
                            }

                            append("Email: ")

                            if (hasMoreThanOneItem) {
                                appendLine()
                            }

                            withStyle(style = spanStyle) {
                                emails.forEachIndexed { index, email ->
                                    if (index != 0) {
                                        appendLine()
                                    }

                                    append(email)
                                }
                            }
                        }
                    )
                }

                if (addresses.isNotEmpty()) {
                    list.add(
                        buildAnnotatedString {
                            val hasMoreThanOneItem = addresses.count() > 1

                            if (hasMoreThanOneItem || emails.count() > 1) {
                                appendLine()
                            }

                            append("Addresse: ")

                            if (hasMoreThanOneItem) {
                                appendLine()
                            }

                            withStyle(style = spanStyle) {
                                addresses.forEachIndexed { index, address ->
                                    if (index != 0) {
                                        appendLine()
                                    }

                                    append(address)
                                }
                            }
                        }
                    )
                }

                if (urls.isNotEmpty()) {
                    list.add(
                        buildAnnotatedString {
                            val hasMoreThanOneItem = urls.count() > 1

                            if (hasMoreThanOneItem || addresses.count() > 1) {
                                appendLine()
                            }

                            append("Lien: ")

                            if (hasMoreThanOneItem) {
                                appendLine()
                            }

                            withStyle(style = spanStyle) {
                                urls.forEachIndexed { index, url ->
                                    if (index != 0) {
                                        appendLine()
                                    }

                                    append(url)
                                }
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
    }
}