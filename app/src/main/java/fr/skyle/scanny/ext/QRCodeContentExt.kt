package fr.skyle.scanny.ext

import android.os.Parcelable
import fr.skyle.scanny.enums.QRType
import kotlinx.parcelize.Parcelize

interface StringConverter {
    fun asString(): String
}

@Parcelize
sealed class QRCodeContent(val type: QRType) : Parcelable, StringConverter {
    data class QRCodeTextContent(var text: String = "") : QRCodeContent(QRType.TEXT) {
        override fun asString(): String =
            text
    }

    data class QRCodeUrlContent(var url: String = "") : QRCodeContent(QRType.URL) {
        override fun asString(): String =
            url
    }
}