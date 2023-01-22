package fr.skyle.scanny.utils.qrCode

import fr.skyle.scanny.enums.QRType
import fr.skyle.scanny.enums.WifiEncryptionType

sealed class QRCodeContent(val type: QRType, val rawData: String?) {

    class TextContent(
        var text: String,
        rawData: String? = null
    ) : QRCodeContent(QRType.TEXT, rawData)

    class UrlContent(
        var url: String,
        rawData: String? = null
    ) : QRCodeContent(QRType.URL, rawData)

    class SMSContent(
        var phoneNumber: String? = null,
        var message: String? = null,
        rawData: String? = null
    ) : QRCodeContent(QRType.SMS, rawData) {

        companion object {
            const val SMS_TO = "SMSTO"
        }
    }

    class EmailContent(
        var email: String,
        rawData: String? = null
    ) : QRCodeContent(QRType.EMAIL, rawData) {

        companion object {
            const val MAILTO = "MAILTO"
        }
    }

    class EmailMessageContent(
        var email: String? = null,
        var subject: String? = null,
        var body: String? = null,
        rawData: String? = null
    ) : QRCodeContent(QRType.EMAIL_MSG, rawData) {

        companion object {
            const val BEGIN = "MATMSG"
            const val TO = "TO"
            const val SUBJECT = "SUB"
            const val BODY = "BODY"
            const val END = ";"
        }
    }

    class WiFiContent(
        var ssid: String? = null,
        var encryptionType: WifiEncryptionType? = null,
        var password: String? = null,
        var isHidden: Boolean? = null,
        rawData: String? = null
    ) : QRCodeContent(QRType.WIFI, rawData) {

        companion object {
            const val WIFI = "WIFI"
            const val SSID = "S"
            const val TYPE = "T"
            const val PASSWORD = "P"
            const val HIDDEN = "H"
            const val END = ";"
        }
    }

    class ContactContent(
        var firstName: String? = null,
        var lastName: String? = null,
        rawData: String? = null
    ) : QRCodeContent(QRType.CONTACT, rawData) {

        companion object {
            const val BEGIN = "BEGIN:VCARD"
            const val NAMES = "N"
            const val TEL_TYPE = "TEL;TYPE"
            const val EMAIL = "EMAIL"
            const val COMPANY = "ORG"
            const val PROFESSION = "TITLE"
            const val ADR_TYPE = "ADR;TYPE"
            const val URL = "URL"
            const val VERSION = "VERSION:3.0"
            const val END = "END:VCARD"
        }
    }
}