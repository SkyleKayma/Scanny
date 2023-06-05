package fr.skyle.scanny.ext

import android.content.Context


// TODO Delete Use Like in PrySystem
internal fun Float.toDp(context: Context): Float {
    val screenPixelDensity = context.resources.displayMetrics.density
    return this / screenPixelDensity
}