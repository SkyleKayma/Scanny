package fr.skyle.scanny.enums

import androidx.annotation.Keep

@Keep
enum class QRType {
    TEXT,
    URL,
    EMAIL,
    EMAIL_MSG,
    SMS,
    WIFI,
    CONTACT
}