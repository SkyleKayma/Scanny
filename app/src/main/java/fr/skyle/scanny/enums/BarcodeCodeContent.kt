package fr.skyle.scanny.enums

import fr.skyle.scanny.data.enums.BarcodeFormat
import fr.skyle.scanny.data.enums.BarcodeType

sealed class BarcodeCodeContent(
    val type: BarcodeType,
    val format: BarcodeFormat? = null,
    val rawData: String? = null
) {
    class TextContent(
        var text: String?,
        format: BarcodeFormat? = null,
        rawData: String? = null
    ) : BarcodeCodeContent(BarcodeType.TEXT, format, rawData)

    class UrlContent(
        var url: String?,
        format: BarcodeFormat? = null,
        rawData: String? = null
    ) : BarcodeCodeContent(BarcodeType.URL, format, rawData)

    class SMSContent(
        var phoneNumber: String?,
        var message: String?,
        format: BarcodeFormat? = null,
        rawData: String? = null
    ) : BarcodeCodeContent(BarcodeType.SMS, format, rawData) {

        companion object {
            const val SMS_TO = "SMSTO"
        }
    }

    class EmailContent(
        var email: String?,
        format: BarcodeFormat? = null,
        rawData: String? = null
    ) : BarcodeCodeContent(BarcodeType.EMAIL, format, rawData) {

        companion object {
            const val MAILTO = "MAILTO"
        }
    }

    class EmailMessageContent(
        var email: String?,
        var subject: String?,
        var body: String?,
        format: BarcodeFormat? = null,
        rawData: String? = null
    ) : BarcodeCodeContent(BarcodeType.EMAIL_MSG, format, rawData) {

        companion object {
            const val BEGIN = "MATMSG"
            const val TO = "TO"
            const val SUBJECT = "SUB"
            const val BODY = "BODY"
            const val END = ";"
        }
    }

    class WiFiContent(
        var ssid: String?,
        var encryptionType: WifiEncryptionType?,
        var password: String?,
        format: BarcodeFormat? = null,
        rawData: String? = null
    ) : BarcodeCodeContent(BarcodeType.WIFI, format, rawData) {

        companion object {
            const val WIFI = "WIFI"
            const val SSID = "S"
            const val TYPE = "T"
            const val PASSWORD = "P"
            const val END = ";"
        }
    }

    class ContactContent(
        var names: List<String>, // LASTNAME; FIRSTNAME; ADDITIONAL NAME
        var formattedName: String?,
        var org: String?,
        var title: String?,
        var tels: List<String>,
        var emails: List<String>,
        var addresses: List<String>,
        var urls: List<String>,
        format: BarcodeFormat? = null,
        rawData: String? = null
    ) : BarcodeCodeContent(BarcodeType.CONTACT, format, rawData) {

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