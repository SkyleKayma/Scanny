package fr.skyle.scanny.utils.qrCode

import io.github.g0dkar.qrcode.ErrorCorrectionLevel
import io.github.g0dkar.qrcode.MaskPattern
import io.github.g0dkar.qrcode.QRCode.Companion.DEFAULT_CELL_SIZE
import io.github.g0dkar.qrcode.QRCode.Companion.DEFAULT_MARGIN
import io.github.g0dkar.qrcode.render.Colors

data class QRCodeData(
    val content: String,
    val cellSize: Int = DEFAULT_CELL_SIZE,
    val margin: Int = DEFAULT_MARGIN,
    val maskPattern: MaskPattern = MaskPattern.PATTERN001,
    val errorCorrectionLevel: ErrorCorrectionLevel = ErrorCorrectionLevel.L,
    val brightColor:Int = Colors.WHITE,
    val darkColor: Int = Colors.BLACK,
    val marginColor: Int = Colors.WHITE
)