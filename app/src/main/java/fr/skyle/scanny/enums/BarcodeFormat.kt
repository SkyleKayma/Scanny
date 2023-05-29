package fr.skyle.scanny.enums

enum class BarcodeFormat {
    CODE_128,
    CODE_39,
    CODE_93,
    BARCODE,
    DATA_MATRIX,
    EAN_13,
    EAN_8,
    ITF,
    QR_CODE,
    UPC_A,
    UPC_E,
    PDF417,
    AZTEC;

    companion object
}