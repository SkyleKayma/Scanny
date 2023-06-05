package fr.skyle.scanny.ext

import com.google.mlkit.vision.barcode.common.Barcode
import fr.skyle.scanny.data.enums.BarcodeFormat
import fr.skyle.scanny.enums.WifiEncryptionType
import fr.skyle.scanny.enums.BarcodeCodeContent


val Barcode?.toBarcodeCodeContent: BarcodeCodeContent
    get() = when (this?.valueType) {
        Barcode.TYPE_TEXT ->
            BarcodeCodeContent.TextContent(
                rawData = rawValue,
                format = BarcodeFormat.fromValue(format),
                text = rawValue ?: ""
            )

        Barcode.TYPE_URL ->
            BarcodeCodeContent.UrlContent(
                rawData = rawValue ?: "",
                format = BarcodeFormat.fromValue(format),
                url = url?.url ?: ""
            )

        Barcode.TYPE_EMAIL ->
            BarcodeCodeContent.EmailMessageContent(
                rawData = rawValue ?: "",
                format = BarcodeFormat.fromValue(format),
                email = email?.address,
                subject = email?.subject,
                body = email?.body
            )

        Barcode.TYPE_SMS ->
            BarcodeCodeContent.SMSContent(
                rawData = rawValue ?: "",
                format = BarcodeFormat.fromValue(format),
                phoneNumber = sms?.phoneNumber,
                message = sms?.message
            )

        Barcode.TYPE_WIFI ->
            BarcodeCodeContent.WiFiContent(
                rawData = rawValue ?: "",
                format = BarcodeFormat.fromValue(format),
                ssid = wifi?.ssid,
                encryptionType = WifiEncryptionType.fromBarcodeWifi(wifi),
                password = wifi?.password
            )

        Barcode.TYPE_CONTACT_INFO ->
            BarcodeCodeContent.ContactContent(
                rawData = rawValue ?: "",
                format = BarcodeFormat.fromValue(format),
                formattedName = contactInfo?.name?.formattedName,
                names = listOfNotNull(contactInfo?.name?.first, contactInfo?.name?.middle, contactInfo?.name?.last),
                org = contactInfo?.organization,
                title = contactInfo?.title,
                emails = contactInfo?.emails?.mapNotNull { it.address } ?: listOf(),
                tels = contactInfo?.phones?.mapNotNull { it.number } ?: listOf(),
                addresses = contactInfo?.addresses?.mapNotNull { it.addressLines.map { it } }?.flatten() ?: listOf(),
                urls = contactInfo?.urls ?: listOf()
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
        else -> BarcodeCodeContent.TextContent(
            rawData = this?.rawValue ?: "",
            format = BarcodeFormat.fromValue(this?.format),
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