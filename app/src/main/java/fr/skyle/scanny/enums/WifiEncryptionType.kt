package fr.skyle.scanny.enums

import com.google.mlkit.vision.barcode.common.Barcode

enum class WifiEncryptionType {
    WEP,
    WPA_WPA2,
    NONE;

    companion object {

        fun fromBarcodeWifi(barcodeWifi: Barcode.WiFi?): WifiEncryptionType =
            when (barcodeWifi?.encryptionType) {
                Barcode.WiFi.TYPE_WEP -> WEP
                Barcode.WiFi.TYPE_WPA -> WPA_WPA2
                else -> NONE
            }
    }
}