package fr.skyle.scanny.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.*
import androidx.compose.runtime.*
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import fr.skyle.scanny.navigation.ScannyNavHost
import fr.skyle.scanny.theme.SCAppTheme
import fr.skyle.scanny.ui.core.SystemIconsColor
import fr.skyle.scanny.utils.qrCode.QRCodeContent


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen(
    navigateToDataPrivacy: () -> Unit,
    navigateToRateApp: () -> Unit,
    navigateToAppSettings: () -> Unit,
    navigateToOpenium: () -> Unit,
    onShareContent: (String) -> Unit,
    onOpenLink: (String) -> Unit,
    onSendEmail: (QRCodeContent.EmailMessageContent) -> Unit,
    onSendSMS: (QRCodeContent.SMSContent) -> Unit,
    onConnectToWifi: (QRCodeContent.WiFiContent) -> Unit,
    onAddToContact: (QRCodeContent.ContactContent) -> Unit
) {
    // Nav
    val navController = rememberAnimatedNavController()

    // Set system icons color
    SystemIconsColor(statusBarColor = SCAppTheme.colors.transparent)

    ScannyNavHost(
        navHostController = navController,
        navigateToDataPrivacy = navigateToDataPrivacy,
        navigateToRateApp = navigateToRateApp,
        navigateToAppSettings = navigateToAppSettings,
        navigateToOpenium = navigateToOpenium,
        onShareContent = onShareContent,
        onOpenLink = onOpenLink,
        onSendEmail = onSendEmail,
        onSendSMS = onSendSMS,
        onConnectToWifi = onConnectToWifi,
        onAddToContact = onAddToContact
    )
}