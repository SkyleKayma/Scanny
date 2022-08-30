package fr.skyle.scanny.ext

import android.annotation.TargetApi
import android.os.Build
import android.os.Parcelable
import androidx.navigation.NavBackStackEntry

@TargetApi(Build.VERSION_CODES.BASE)
fun <T : Parcelable> NavBackStackEntry.getParcelable(key: String, clazz: Class<T>): T? =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arguments?.getParcelable(key, clazz)
    } else arguments?.getParcelable(key)