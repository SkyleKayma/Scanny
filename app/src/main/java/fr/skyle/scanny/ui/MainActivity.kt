package fr.skyle.scanny.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import fr.skyle.scanny.R
import fr.skyle.scanny.theme.SCTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Needed for insets
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            SCTheme {
                MainScreen(
                    onShowDataPrivacy = {
                        showDataPrivacy()
                    },
                    onShowRateTheAppScreen = {
                        showRateTheAppScreen()
                    },
                    onGoToAppSettings = {
                        showAppSettings()
                    }
                )
            }
        }
    }

    private fun showDataPrivacy() {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.app_data_privacy_url))))
    }

    private fun showRateTheAppScreen() {
        val marketIntent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=${packageName}")).apply {
            addFlags(
                Intent.FLAG_ACTIVITY_NO_HISTORY or
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK
            )
        }

        if (marketIntent.resolveActivity(packageManager) != null) {
            startActivity(marketIntent)
        } else startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=${packageName}")))
    }

    private fun showAppSettings() {
        startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", packageName, null)
        })
    }
}