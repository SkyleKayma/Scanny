package fr.skyle.scanny.utils.qrCode

import android.os.Parcelable
import fr.skyle.scanny.enums.QRType
import fr.skyle.scanny.enums.WifiEncryptionType
import kotlinx.parcelize.Parcelize

interface StringConverter {
    fun asEncodedString(): String
}

@Parcelize
sealed class QRCodeContent(val type: QRType) : Parcelable, StringConverter {

    data class TextContent(
        var text: String
    ) : QRCodeContent(QRType.TEXT) {

        override fun asEncodedString(): String =
            text
    }

    data class UrlContent(
        var url: String
    ) : QRCodeContent(QRType.URL) {

        // Format
        // http://test.com
        override fun asEncodedString(): String =
            url
    }

    data class SMSContent(
        var phoneNumber: String? = null,
        var message: String? = null
    ) : QRCodeContent(QRType.SMS) {

        companion object {
            private const val SMS_TO = "SMSTO"
        }

        // Format
        // SMSTO:+33122334455:Message
        override fun asEncodedString(): String =
            mutableListOf<String>().apply {
                // Begin (mandatory)
                add(SMS_TO)

                // Tel (optional)
                add(phoneNumber ?: "")

                // Message (optional)
                add(message ?: "")
            }.joinToString(":")
    }

    data class EmailContent(
        var email: String
    ) : QRCodeContent(QRType.EMAIL) {

        companion object {
            private const val MAILTO = "MAILTO"
        }

        override fun asEncodedString(): String =
            mutableListOf<String>().apply {
                add(MAILTO)
                add(email)
            }.joinToString(":")
    }

    data class EmailMessageContent(
        var email: String? = null,
        var subject: String? = null,
        var body: String? = null
    ) : QRCodeContent(QRType.EMAIL_MSG) {

        companion object {
            private const val BEGIN = "MATMSG"
            private const val TO = "TO"
            private const val SUBJECT = "SUB"
            private const val BODY = "BODY"
            private const val END = ";"
        }

        // Format:
        // MATMSG:TO:mail@test.com;SUB:Subject;BODY:Message;;
        override fun asEncodedString(): String =
            buildString {
                // Prefix
                append("$BEGIN:")

                // Email
                email?.let {
                    append("$TO:")
                    append("$it;")
                }

                // Subject
                subject?.let {
                    append("$SUBJECT:")
                    append("$it;")
                }

                // Body
                body?.let {
                    append("$BODY:")
                    append("$it;")
                }

                // End
                append(END)
            }
    }

    data class WiFiContent(
        var ssid: String? = null,
        var encryptionType: WifiEncryptionType? = null,
        var password: String? = null,
        var isHidden: Boolean? = null
    ) : QRCodeContent(QRType.WIFI) {

        companion object {
            private const val WIFI = "WIFI"
            private const val SSID = "S"
            private const val TYPE = "T"
            private const val PASSWORD = "P"
            private const val HIDDEN = "H"
            private const val END = ";"
        }

        // WIFI:S:NEUF_0809;T:WEP;P:Password;H:true
        override fun asEncodedString(): String =
            buildString {
                append("$WIFI:")

                // SSID
                ssid?.let {
                    append("$SSID:")
                    append("$it;")
                }

                encryptionType?.let { type ->
                    // Encryption type
                    append("$TYPE:")
                    append("${type.name};")

                    // Password
                    password?.let {
                        if (type != WifiEncryptionType.NONE) {
                            append("$PASSWORD:")
                            append("$it;")
                        }
                    }
                }

                // Hidden
                if (isHidden == true) {
                    append("$HIDDEN:")
                    append("${isHidden.toString()};")
                }

                append(END)
            }
    }

    data class ContactContent(
        var firstName: String? = null,
        var lastName: String? = null
    ) : QRCodeContent(QRType.CONTACT) {

        companion object {
            private const val BEGIN = "BEGIN:VCARD"
            private const val NAMES = "N"
            private const val TEL_TYPE = "TEL;TYPE"
            private const val EMAIL = "EMAIL"
            private const val COMPANY = "ORG"
            private const val PROFESSION = "TITLE"
            private const val ADR_TYPE = "ADR;TYPE"
            private const val URL = "URL"
            private const val VERSION = "VERSION:3.0"
            private const val END = "END:VCARD"
        }

        override fun asEncodedString(): String =
            buildString {
                // Begin
                appendLine(BEGIN)

                // Names
                if (firstName != null || lastName != null) {
                    append(NAMES)
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
                appendLine(VERSION)
                appendLine(END)
            }
    }
}