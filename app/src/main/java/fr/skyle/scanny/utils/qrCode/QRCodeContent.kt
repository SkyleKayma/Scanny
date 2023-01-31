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
        rawData: String? = null
    ) : QRCodeContent(QRType.WIFI, rawData) {

        companion object {
            const val WIFI = "WIFI"
            const val SSID = "S"
            const val TYPE = "T"
            const val PASSWORD = "P"
            const val END = ";"
        }
    }

    class ContactContent(
        var names: List<String>? = null, // LASTNAME; FIRSTNAME; ADDITIONAL NAME
        var formattedName: String? = null,
        var org: String? = null,
        var title: String? = null,
        var tels: List<String>? = null,
        var emails: List<String>? = null,
        var addresses: List<String>? = null,
        var urls: List<String>? = null,
        rawData: String? = null
    ) : QRCodeContent(QRType.CONTACT, rawData) {

        companion object {
            const val BEGIN = "BEGIN:VCARD"
            const val VERSION = "VERSION:3.0"
            const val NAMES = "N:"
            const val FORMATTED_NAME = "FN:"
            const val ORG = "ORG:"
            const val TITLE = "TITLE:"
            const val EMAIL = "EMAIL:"
            const val TEL = "TEL:"
            const val ADDRESS = "ADR:"
            const val URL = "URL:"
            const val END = "END:VCARD"
        }
    }
}