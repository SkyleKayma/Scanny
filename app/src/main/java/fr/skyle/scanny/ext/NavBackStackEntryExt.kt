package fr.skyle.scanny.ext

import android.os.Build
import android.os.Parcelable
import androidx.navigation.NavBackStackEntry
import fr.skyle.scanny.BuildConfig

fun <T : Parcelable> NavBackStackEntry.getParcelable(key: String, clazz: Class<T>): T? =
    if (BuildConfig.VERSION_CODE >= Build.VERSION_CODES.TIRAMISU) {
        arguments?.getParcelable(key, clazz)
    } else arguments?.getParcelable(key)

fun NavBackStackEntry.putParcelable(key: String, parcelable: Parcelable) {
    arguments?.putParcelable(key, parcelable)
}