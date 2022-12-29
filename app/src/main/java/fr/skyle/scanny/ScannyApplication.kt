package fr.skyle.scanny

import android.app.Application
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.HiltAndroidApp
import fr.skyle.scanny.log.FirebaseCrashReportingTree
import fr.skyle.scanny.utils.SCDataStore
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
abstract class ScannyApplication : Application() {

    @Inject
    lateinit var crashlytics: FirebaseCrashlytics

    @Inject
    lateinit var dataStore: SCDataStore

    override fun onCreate() {
        super.onCreate()

        // Configuration
        initTimber()

        // Preferences
        updateVersionCodeInPreferences()
    }

    // --- Configuration
    // ---------------------------------------------------

    open fun initTimber() {
        Timber.plant(FirebaseCrashReportingTree(crashlytics))
    }

    // --- Preferences
    // ---------------------------------------------------

    private fun updateVersionCodeInPreferences() {
        runBlocking {
            dataStore.setCurrentBuildVersion(BuildConfig.VERSION_CODE)
        }
    }
}