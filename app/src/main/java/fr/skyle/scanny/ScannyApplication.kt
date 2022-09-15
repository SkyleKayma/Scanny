package fr.skyle.scanny

import android.app.Application
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.HiltAndroidApp
import fr.skyle.scanny.log.FirebaseCrashReportingTree
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
abstract class ScannyApplication : Application() {

    @Inject
    lateinit var crashlytics: FirebaseCrashlytics

    override fun onCreate() {
        super.onCreate()

        initTimber()
    }

    // --- Init methods
    // ---------------------------------------------------

    open fun initTimber() {
        Timber.plant(FirebaseCrashReportingTree(crashlytics))
    }
}