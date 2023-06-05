package fr.skyle.scanny.ext

import fr.skyle.scanny.R
import fr.skyle.scanny.data.enums.BarcodeType

val BarcodeType.iconId: Int
    get() = when (this) {
        BarcodeType.TEXT ->
            R.drawable.ic_qr_text

        BarcodeType.CONTACT ->
            R.drawable.ic_qr_contact

        BarcodeType.URL ->
            R.drawable.ic_qr_web_url

        BarcodeType.WIFI ->
            R.drawable.ic_qr_wifi

        BarcodeType.EMAIL ->
            R.drawable.ic_qr_mail

        BarcodeType.EMAIL_MSG ->
            R.drawable.ic_qr_mail

        BarcodeType.SMS ->
            R.drawable.ic_qr_sms
    }

val BarcodeType.textId: Int
    get() = when (this) {
        BarcodeType.TEXT ->
            R.string.generate_text

        BarcodeType.CONTACT ->
            R.string.generate_contact

        BarcodeType.URL ->
            R.string.generate_web_url

        BarcodeType.WIFI ->
            R.string.generate_wifi

        BarcodeType.EMAIL ->
            R.string.generate_email

        BarcodeType.EMAIL_MSG ->
            R.string.generate_email

        BarcodeType.SMS ->
            R.string.generate_sms
    }