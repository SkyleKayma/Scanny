package fr.skyle.scanny.enums

import com.google.mlkit.vision.barcode.common.Barcode


sealed interface ModalType {
    data class ScanSuccessModal(var barcode: Barcode) : ModalType
}