package fr.skyle.scanny.ext

import com.google.mlkit.vision.barcode.common.Barcode
import fr.skyle.scanny.enums.WifiEncryptionType

fun WifiEncryptionType.Companion.fromBarcodeWifi(barcodeWifi: Barcode.WiFi?): WifiEncryptionType =
    when (barcodeWifi?.encryptionType) {
        Barcode.WiFi.TYPE_WEP ->
            WifiEncryptionType.WEP

        Barcode.WiFi.TYPE_WPA ->
            WifiEncryptionType.WPA_WPA2

        else ->
            WifiEncryptionType.NONE
    }