package fr.skyle.scanny

import timber.log.Timber

class ScannyApplicationImpl : ScannyApplication() {

    override fun initTimber() {
        super.initTimber()
        Timber.plant(Timber.DebugTree())
    }
}