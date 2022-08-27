package fr.skyle.scanny.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import fr.skyle.scanny.R
import fr.skyle.scanny.ui.destinations.*

// --- Main Routes
// -------------------------------------------

// Splash
const val splashRoute = "splash"

// Scan route
const val scanRoute = "scan"

// Generator route
const val generatorRoute = "generator"

// History route
const val scanHistoryRoute = "history"

// Settings route
const val settingsRoute = "settings"

// --- Other routes
// -------------------------------------------

// Create QR Text route
const val createQRTextRoute = "createQRText"

// Generate QR route
const val generateQRRoute = "generateQR"
const val ARG_QR_CODE_DATA = "ARG_QR_CODE_DATA"


sealed class BottomBarScreens(
    val direction: DirectionDestination,
    @StringRes val textId: Int,
    @DrawableRes val iconId: Int,
    @DrawableRes val animatedIconId: Int
) {
    object QRScan : BottomBarScreens(
        ScanScreenDestination, R.string.home_scan, R.drawable.ic_scan_qr, R.drawable.ic_scan_qr_animated
    )

    object QRHistory : BottomBarScreens(
        HistoryScreenDestination, R.string.home_history, R.drawable.ic_history_qr, R.drawable.ic_history_qr_animated
    )

    object QRGenerator : BottomBarScreens(
        GeneratorScreenDestination, R.string.home_generate, R.drawable.ic_gen_qr, R.drawable.ic_gen_qr_animated
    )

    object Settings : BottomBarScreens(
        SettingsScreenDestination, R.string.home_settings, R.drawable.ic_settings, R.drawable.ic_settings_animated
    )

    companion object {
        fun values() =
            listOf(QRScan, QRHistory, QRGenerator, Settings)
    }
}