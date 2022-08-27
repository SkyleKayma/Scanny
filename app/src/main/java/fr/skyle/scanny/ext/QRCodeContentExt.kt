package fr.skyle.scanny.ext

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class QRCodeContent : Parcelable {
    data class QRCodeTextContent(var text: String = "") : QRCodeContent()
}

fun QRCodeContent.asString(): String =
    when (this) {
        is QRCodeContent.QRCodeTextContent ->
            this.convertContentToString()
    }

private fun QRCodeContent.QRCodeTextContent.convertContentToString(): String =
    text
