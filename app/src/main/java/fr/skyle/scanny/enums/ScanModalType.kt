package fr.skyle.scanny.enums

import com.google.mlkit.vision.barcode.common.Barcode


sealed interface ScanModalType {
    data class ScanSuccessScanModal(var barcode: Barcode) : ScanModalType
}