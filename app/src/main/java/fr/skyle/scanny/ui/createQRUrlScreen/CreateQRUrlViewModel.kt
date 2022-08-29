package fr.skyle.scanny.ui.createQRUrlScreen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.skyle.scanny.enums.QRType
import fr.skyle.scanny.ext.QRCodeContent
import fr.skyle.scanny.ext.getEmptyQRCodeData
import javax.inject.Inject

@HiltViewModel
class CreateQRUrlViewModel @Inject constructor() : ViewModel() {

    private val currentQRContent: QRCodeContent.QRCodeUrlContent =
        QRType.URL.getEmptyQRCodeData() as QRCodeContent.QRCodeUrlContent

    fun isContentValid(): Boolean =
        currentQRContent.url.isNotBlank()

    fun setText(newText: String) {
        currentQRContent.url = newText
    }

    fun getQRCodeContent(): QRCodeContent =
        currentQRContent
}