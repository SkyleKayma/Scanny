package fr.skyle.scanny.utils.qrCode

import android.content.Context
import android.graphics.Bitmap
import fr.skyle.scanny.enums.FileType
import fr.skyle.scanny.ext.extension
import fr.skyle.scanny.utils.ShareUtils
import io.github.g0dkar.qrcode.QRCode
import io.github.g0dkar.qrcode.internals.QRCodeSquare
import timber.log.Timber
import java.util.UUID

object QRCodeHelper {

    fun generateQRCode(qrCodeData: QRCodeData): QRCode =
        QRCode(
            data = qrCodeData.content,
            errorCorrectionLevel = qrCodeData.errorCorrectionLevel
        )

    fun renderQRCodeAsBitmap(qrCode: QRCode, qrCodeData: QRCodeData): Bitmap? {
        // QRCode encoded
        val qrCodeEncoded = getQRCodeEncoded(qrCode, qrCodeData)

        // Render
        return try {
            qrCode.render(
                cellSize = qrCodeData.cellSize,
                margin = qrCodeData.margin,
                rawData = qrCodeEncoded,
                brightColor = qrCodeData.brightColor,
                darkColor = qrCodeData.darkColor,
                marginColor = qrCodeData.marginColor
            ).nativeImage() as Bitmap
        } catch (e: Exception) {
            Timber.e(e)
            null
        }
    }

    @Throws
    private fun saveQRCode(qrCode: QRCode, qrCodeData: QRCodeData, fileType: FileType, context: Context) {
        // QRCode encoded
        val qrCodeEncoded = getQRCodeEncoded(qrCode, qrCodeData)

        // Generate UUID
        val uuid = UUID.randomUUID().toString()

        // Render
        val fileName = "$uuid${fileType.extension}"

        ShareUtils.getOutputStream(context, fileName).let { outputStream ->
            qrCode.render(
                cellSize = qrCodeData.cellSize,
                margin = qrCodeData.margin,
                rawData = qrCodeEncoded,
                brightColor = qrCodeData.brightColor,
                darkColor = qrCodeData.darkColor,
                marginColor = qrCodeData.marginColor
            ).writeImage(outputStream)

            outputStream.flush()
            outputStream.close()
        }
    }

    private fun getQRCodeEncoded(qrCode: QRCode, qrCodeData: QRCodeData): Array<Array<QRCodeSquare?>> {
        // Get data type
        val dataTypeValue =
            QRCode.typeForDataAndECL(qrCodeData.content, qrCodeData.errorCorrectionLevel)

        // Encode QRCode
        return qrCode.encode(
            type = dataTypeValue,
            maskPattern = qrCodeData.maskPattern
        )
    }

    fun saveAsPNG(qrCode: QRCode, qrCodeData: QRCodeData, context: Context): Boolean =
        try {
            saveQRCode(qrCode, qrCodeData, FileType.PNG, context)
            true
        } catch (e: Exception) {
            Timber.e(e)
            false
        }
}