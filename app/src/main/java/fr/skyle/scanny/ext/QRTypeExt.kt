package fr.skyle.scanny.ext

import fr.skyle.scanny.R
import fr.skyle.scanny.enum.QRType

val QRType.iconId: Int
    get() = when (this) {
        QRType.TEXT ->
            R.drawable.ic_qr_text
        QRType.CONTACT ->
            R.drawable.ic_qr_contact
        QRType.URL ->
            R.drawable.ic_qr_web_url
        QRType.WIFI ->
            R.drawable.ic_qr_wifi
        QRType.EMAIL ->
            R.drawable.ic_qr_mail
        QRType.SMS ->
            R.drawable.ic_qr_sms
    }

val QRType.textId: Int
    get() = when (this) {
        QRType.TEXT ->
            R.string.generate_qr_text
        QRType.CONTACT ->
            R.string.generate_qr_contact
        QRType.URL ->
            R.string.generate_qr_web_url
        QRType.WIFI ->
            R.string.generate_qr_wifi
        QRType.EMAIL ->
            R.string.generate_qr_email
        QRType.SMS ->
            R.string.generate_qr_sms
    }