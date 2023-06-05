package fr.skyle.scanny.ui.generateQR

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.skyle.scanny.ext.asEncodedString
import fr.skyle.scanny.enums.BarcodeCodeContent
import fr.skyle.scanny.utils.qrCode.QRCodeData
import fr.skyle.scanny.utils.qrCode.QRCodeHelper
import io.github.g0dkar.qrcode.QRCode
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenerateQRViewModel @Inject constructor() : ViewModel() {

    private lateinit var qrCodeData: QRCodeData
    private lateinit var qrCode: QRCode

    private val _bitmapStateFlow = MutableStateFlow<Bitmap?>(null)
    val bitmapSharedFlow = _bitmapStateFlow.asStateFlow()

    fun generateQRCode(barcodeCodeContent: BarcodeCodeContent?) {
        qrCodeData = QRCodeData(content = barcodeCodeContent?.asEncodedString() ?: "")
        qrCode = QRCodeHelper.generateQRCode(qrCodeData)

        render()
    }

    fun saveQRCodeAsFile(context: Context) {
        QRCodeHelper.saveAsPNG(qrCode, qrCodeData, context)
    }

    private fun render(): Job =
        viewModelScope.launch {
            QRCodeHelper.renderQRCodeAsBitmap(qrCode, qrCodeData)?.let { bitmap ->
                _bitmapStateFlow.emit(bitmap)
            }
        }
}