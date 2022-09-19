package fr.skyle.scanny.enums

import androidx.annotation.Keep

@Keep
enum class FileType(val extension: String) {
    PNG(".png"),
    JPEG(".jpg")
}