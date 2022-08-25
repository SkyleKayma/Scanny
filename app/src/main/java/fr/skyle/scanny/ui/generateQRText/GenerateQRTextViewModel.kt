package fr.skyle.scanny.ui.generateQRText

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GenerateQRTextViewModel @Inject constructor() : ViewModel() {

    private val currentData = QRCodeTextData("")

    fun areDataValid(): Boolean =
        currentData.text.isNotBlank()

    fun setText(newText: String) {
        currentData.text = newText
    }

    data class QRCodeTextData(var text: String)
}
