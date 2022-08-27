package fr.skyle.scanny.ui.createQRText

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.skyle.scanny.enum.QRType
import fr.skyle.scanny.ext.QRCodeContent
import fr.skyle.scanny.ext.getEmptyQRCodeData
import javax.inject.Inject

@HiltViewModel
class CreateQRTextViewModel @Inject constructor() : ViewModel() {

    private val currentQRContent: QRCodeContent.QRCodeTextContent =
        QRType.TEXT.getEmptyQRCodeData() as QRCodeContent.QRCodeTextContent

    fun isContentValid(): Boolean =
        currentQRContent.text.isNotBlank()

    fun setText(newText: String) {
        currentQRContent.text = newText
    }

    fun getQRCodeContent(): QRCodeContent =
        currentQRContent
}