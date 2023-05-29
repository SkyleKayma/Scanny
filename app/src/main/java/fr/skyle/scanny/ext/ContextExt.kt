package fr.skyle.scanny.ext

import android.app.Activity
import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.provider.Settings
import androidx.annotation.RequiresApi
import fr.skyle.scanny.R
import fr.skyle.scanny.enums.FeedbackSubject
import fr.skyle.scanny.enums.WifiEncryptionType

/**
 * Use only in Composable !
 */
fun Context.findActivity(): Activity {
    var ctx = this
    while (ctx is ContextWrapper) {
        if (ctx is Activity) {
            return ctx
        }
        ctx = ctx.baseContext
    }
    throw IllegalStateException(
        "Expected an activity context but instead found: $ctx"
    )
}

fun Context.vibrateScan() {
    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        (getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager).defaultVibrator
    } else getSystemService(VIBRATOR_SERVICE) as Vibrator

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(VibrationEffect.createOneShot(200L, VibrationEffect.DEFAULT_AMPLITUDE))
    } else vibrator.vibrate(200L)
}

fun Context.navigateToLink(link: String) {
    startActivity(Intent.createChooser(Intent(Intent.ACTION_VIEW, Uri.parse(link)), null))
}

fun Context.navigateToDataPrivacy() {
    navigateToLink(getString(R.string.app_data_privacy_url))
}

fun Context.navigateToOpenium() {
    navigateToLink(getString(R.string.openium_url))
}

fun Context.navigateToRateApp() {
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

fun Context.navigateToAppSettings() {
    startActivity(
        Intent.createChooser(
            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", packageName, null)
            }, null
        )
    )
}

fun Context.shareTextContent(shareText: String?) {
    startActivity(
        Intent.createChooser(
            Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_TEXT, shareText ?: "")
                type = "text/plain"
            }, null
        )
    )
}

fun Context.sendMail(email: String?, subject: String?, message: String?) {
    startActivity(
        Intent.createChooser(
            Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, email?.let { arrayOf(it) })
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, message)
            }, null
        )
    )
}

fun Context.sendFeedback(subject: FeedbackSubject, message: String) {
    sendMail(getString(R.string.app_email_contact), getString(subject.tag), message)
}

fun Context.sendSMS(phoneNumber: String?, message: String?) {
    startActivity(
        Intent.createChooser(
            Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:${phoneNumber ?: ""}")).apply {
                putExtra("sms_body", message ?: "")
            }, null
        )
    )
}

@RequiresApi(Build.VERSION_CODES.R)
fun WifiManager.connectToWifi(
    ssid: String,
    encryptionType: WifiEncryptionType?,
    password: String?
) {
    // TODO
//    val suggestion =
//        WifiNetworkSuggestion.Builder()
//            .setSsid(ssid)
//            .setIsAppInteractionRequired(true)
//            .apply {
//
//                if (password != null && encryptionType == WifiEncryptionType.WPA_WPA2) {
//                    setWpa2Passphrase(password)
//                }
//            }
//            .build()
//
//    // Remove previously named network
//    removeNetworkSuggestions(listOf(suggestion))
//
//    // Add this new network
//    addNetworkSuggestions(listOf(suggestion))
//
//    launcher.launch(
//        Intent.createChooser(
//            Intent(Settings.ACTION_WIFI_ADD_NETWORKS).apply {
//                putParcelableArrayListExtra(Settings.EXTRA_WIFI_NETWORK_LIST, arrayListOf(suggestion.build()))
//            },
//            null
//        )
//    )
}