package fr.skyle.scanny.ext

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp

fun Int.pxToDp(density: Density): Dp =
    with(density) {
        this@pxToDp.toDp()
    }