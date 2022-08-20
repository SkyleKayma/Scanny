package fr.skyle.scanny

import timber.log.Timber

class AppImpl : ScannyApplication() {

    override fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }
}