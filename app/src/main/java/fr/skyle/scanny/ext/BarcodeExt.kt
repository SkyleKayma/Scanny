package fr.skyle.scanny.ext

import com.google.mlkit.vision.barcode.common.Barcode
import fr.skyle.scanny.enums.WifiEncryptionType
import fr.skyle.scanny.utils.qrCode.QRCodeContent


val Barcode.toQRCodeContent: QRCodeContent
    get() = when (this.valueType) {
        Barcode.TYPE_TEXT ->
            QRCodeContent.TextContent(
                text = rawValue ?: ""
            )
        Barcode.TYPE_URL ->
            QRCodeContent.UrlContent(
                url = url?.url ?: ""
            )
        Barcode.TYPE_EMAIL ->
            // The scan can tell us if it's uses MATMSG or MAILTO format
            // So we use the format that can handle the most data and hope that MLKit handle the rest
            QRCodeContent.EmailMessageContent(
                email = email?.address,
                subject = email?.subject,
                body = email?.body
            )
        Barcode.TYPE_SMS ->
            QRCodeContent.SMSContent(
                phoneNumber = sms?.phoneNumber,
                message = sms?.message
            )
        Barcode.TYPE_WIFI ->
            QRCodeContent.WiFiContent(
                ssid = wifi?.ssid,
                encryptionType = WifiEncryptionType.fromBarcodeWifi(wifi),
                password = wifi?.password,
                isHidden = null
            )
        Barcode.TYPE_CONTACT_INFO ->
            QRCodeContent.ContactContent(
                contactInfo?.name?.first,
                contactInfo?.name?.last
            ) // TODO
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
        else -> QRCodeContent.TextContent(rawValue ?: "")
    }