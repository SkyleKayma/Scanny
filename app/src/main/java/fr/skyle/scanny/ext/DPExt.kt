package fr.skyle.scanny.ext

import android.content.Context

internal fun Int.toDp(context: Context): Float =
    toFloat().toDp(context)

internal fun Float.toDp(context: Context): Float {
    val screenPixelDensity = context.resources.displayMetrics.density
    return this / screenPixelDensity
}


