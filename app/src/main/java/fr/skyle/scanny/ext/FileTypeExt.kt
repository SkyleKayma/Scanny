package fr.skyle.scanny.ext

import fr.skyle.scanny.enums.FileType

val FileType.extension: String
    get() = when (this) {
        FileType.PNG -> ".png"
        FileType.JPEG -> ".jpg"
    }