package fr.skyle.scanny

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class ScannyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

    }
}