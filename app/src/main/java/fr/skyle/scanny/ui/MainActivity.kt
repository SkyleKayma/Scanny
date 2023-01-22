package fr.skyle.scanny.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.net.wifi.WifiNetworkSuggestion
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import fr.skyle.scanny.R
import fr.skyle.scanny.enums.WifiEncryptionType
import fr.skyle.scanny.theme.SCTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Needed for insets
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            SCTheme {
                MainScreen(
                    navigateToDataPrivacy = {
                        navigateToDataPrivacy()
                    },
                    navigateToRateApp = {
                        navigateToRateApp()
                    },
                    navigateToAppSettings = {
                        navigateToAppSettings()
                    },
                    navigateToOpenium = {
                        navigateToOpenium()
                    },
                    onShareContent = {
                        shareTextContent(it)
                    },
                    onOpenLink = {
                        navigateToLink(it.url)
                    },
                    onSendEmail = {
                        sendEmail(it.email, it.subject, it.body)
                    },
                    onSendSMS = {
                        sendSMS(it.phoneNumber, it.message)
                    },
                    onConnectToWifi = {
                        connectToWifi(it.ssid, it.encryptionType, it.password, it.isHidden)
                    },
                    onAddToContact = {
                        // TODO
                    }
                )
            }
        }
    }

    private fun navigateToDataPrivacy() {
        navigateToLink(getString(R.string.app_data_privacy_url))
    }

    private fun navigateToLink(link: String) {
        startActivity(
            Intent.createChooser(
                Intent(Intent.ACTION_VIEW, Uri.parse(link)),
                null
            )
        )
    }

    private fun navigateToRateApp() {
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

    private fun navigateToAppSettings() {
        startActivity(
            Intent.createChooser(
                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", packageName, null)
                },
                null
            )
        )
    }

    private fun navigateToOpenium() {
        startActivity(
            Intent.createChooser(
                Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.openium_url))),
                null
            )
        )
    }

    private fun shareTextContent(shareText: String) {
        startActivity(
            Intent.createChooser(
                Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, shareText)
                    type = "text/plain"
                },
                null
            )
        )
    }

    private fun sendEmail(email: String?, subject: String?, message: String?) {
        startActivity(
            Intent.createChooser(
                Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:")
                    putExtra(Intent.EXTRA_EMAIL, email?.let { arrayOf(it) })
                    putExtra(Intent.EXTRA_SUBJECT, subject)
                    putExtra(Intent.EXTRA_TEXT, message)
                },
                null
            )
        )
    }

    private fun sendSMS(phoneNumber: String?, message: String?) {
        startActivity(
            Intent.createChooser(
                Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:${phoneNumber ?: ""}")).apply {
                    putExtra("sms_body", message ?: "")
                },
                null
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun connectToWifi(ssid: String?, encryptionType: WifiEncryptionType?, password: String?, isHidden: Boolean?) {
        val suggestion = WifiNetworkSuggestion.Builder()

        ssid?.let {
            suggestion.setSsid(it)
        }

        if (encryptionType != null && password != null) {
            when (encryptionType) {
                WifiEncryptionType.WEP -> {
                    // Not handle by the API
                }
                WifiEncryptionType.WPA_WPA2 ->
                    suggestion.setWpa2Passphrase(password)
                WifiEncryptionType.NONE ->
                    suggestion.setWpa3Passphrase(password)
            }
        }

        isHidden?.let {
            suggestion.setIsHiddenSsid(it)
        }

        startActivity(
            Intent.createChooser(
                Intent(Settings.ACTION_WIFI_ADD_NETWORKS).apply {
                    putParcelableArrayListExtra(Settings.EXTRA_WIFI_NETWORK_LIST, arrayListOf(suggestion.build()))
                },
                null
            )
        )
    }
}