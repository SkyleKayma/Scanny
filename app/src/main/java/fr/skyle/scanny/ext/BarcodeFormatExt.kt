package fr.skyle.scanny.ext

import androidx.annotation.StringRes
import com.google.mlkit.vision.barcode.common.Barcode
import fr.skyle.scanny.R
import fr.skyle.scanny.data.enums.BarcodeFormat

fun BarcodeFormat.Companion.fromValue(value: Int?): BarcodeFormat? =
    BarcodeFormat.values().find { it.value == value }

val BarcodeFormat.value: Int
    get() = when (this) {
        BarcodeFormat.CODE_128 ->
            Barcode.FORMAT_CODE_128

        BarcodeFormat.CODE_39 ->
            Barcode.FORMAT_CODE_39

        BarcodeFormat.CODE_93 ->
            Barcode.FORMAT_CODE_93

        BarcodeFormat.BARCODE ->
            Barcode.FORMAT_CODABAR

        BarcodeFormat.DATA_MATRIX ->
            Barcode.FORMAT_DATA_MATRIX

        BarcodeFormat.EAN_13 ->
            Barcode.FORMAT_EAN_13

        BarcodeFormat.EAN_8 ->
            Barcode.FORMAT_EAN_8

        BarcodeFormat.ITF ->
            Barcode.FORMAT_ITF

        BarcodeFormat.QR_CODE ->
            Barcode.FORMAT_QR_CODE

        BarcodeFormat.UPC_A ->
            Barcode.FORMAT_UPC_A

        BarcodeFormat.UPC_E ->
            Barcode.FORMAT_UPC_E

        BarcodeFormat.PDF417 ->
            Barcode.FORMAT_PDF417

        BarcodeFormat.AZTEC ->
            Barcode.FORMAT_AZTEC
    }

val BarcodeFormat.textId
    @StringRes
    get() = when (this) {
        BarcodeFormat.CODE_128 ->
            R.string.barcode_format_code_128

        BarcodeFormat.CODE_39 ->
            R.string.barcode_format_code_39

        BarcodeFormat.CODE_93 ->
            R.string.barcode_format_code_93

        BarcodeFormat.BARCODE ->
            R.string.barcode_format_barcode

        BarcodeFormat.DATA_MATRIX ->
            R.string.barcode_format_data_matrix

        BarcodeFormat.EAN_13 ->
            R.string.barcode_format_ean_13

        BarcodeFormat.EAN_8 ->
            R.string.barcode_format_ean_8

        BarcodeFormat.ITF ->
            R.string.barcode_format_itf

        BarcodeFormat.QR_CODE ->
            R.string.barcode_format_qr_code

        BarcodeFormat.UPC_A ->
            R.string.barcode_format_upc_a

        BarcodeFormat.UPC_E ->
            R.string.barcode_format_upc_e

        BarcodeFormat.PDF417 ->
            R.string.barcode_format_pdf417

        BarcodeFormat.AZTEC ->
            R.string.barcode_format_aztec
    }