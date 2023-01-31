package fr.skyle.scanny.ext

import com.google.mlkit.vision.barcode.common.Barcode
import fr.skyle.scanny.enums.WifiEncryptionType
import fr.skyle.scanny.utils.qrCode.QRCodeContent


val Barcode?.toQRCodeContent: QRCodeContent
    get() = when (this?.valueType) {
        Barcode.TYPE_TEXT ->
            QRCodeContent.TextContent(
                rawData = rawValue,
                text = rawValue ?: ""
            )

        Barcode.TYPE_URL ->
            QRCodeContent.UrlContent(
                rawData = rawValue ?: "",
                url = url?.url ?: ""
            )

        Barcode.TYPE_EMAIL ->
            QRCodeContent.EmailMessageContent(
                rawData = rawValue ?: "",
                email = email?.address,
                subject = email?.subject,
                body = email?.body
            )

        Barcode.TYPE_SMS ->
            QRCodeContent.SMSContent(
                rawData = rawValue ?: "",
                phoneNumber = sms?.phoneNumber,
                message = sms?.message
            )

        Barcode.TYPE_WIFI ->
            QRCodeContent.WiFiContent(
                rawData = rawValue ?: "",
                ssid = wifi?.ssid,
                encryptionType = WifiEncryptionType.fromBarcodeWifi(wifi),
                password = wifi?.password
            )

        Barcode.TYPE_CONTACT_INFO ->
            QRCodeContent.ContactContent(
                rawData = rawValue ?: "",
                formattedName = contactInfo?.name?.formattedName,
                names = listOfNotNull(contactInfo?.name?.first, contactInfo?.name?.middle, contactInfo?.name?.last),
                org = contactInfo?.organization,
                title = contactInfo?.title,
                emails = contactInfo?.emails?.mapNotNull { it.address },
                tels = contactInfo?.phones?.mapNotNull { it.number },
                addresses = contactInfo?.addresses?.mapNotNull { it.addressLines.map { it } }?.flatten(),
                urls = contactInfo?.urls
            )
//        Barcode.TYPE_PHONE -> {
//
//        }
//        Barcode.TYPE_CALENDAR_EVENT -> {
//
//        }
//        Barcode.TYPE_DRIVER_LICENSE -> {
//
//        }
//        Barcode.TYPE_GEO -> {
//
//        }
//        Barcode.TYPE_PRODUCT -> {
//
//        }
//        Barcode.TYPE_ISBN -> {
//
//        }
        else -> QRCodeContent.TextContent(
            rawData = this?.rawValue ?: "",
            text = this?.rawValue ?: ""
        )
    }

val Barcode.PersonName.name: String?
    get() = when {
        first != null && last != null ->
            "$first $last"

        first != null ->
            first

        last != null ->
            last

        else -> null
    }