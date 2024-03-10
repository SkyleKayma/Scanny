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
    onAddToContact: (QRCodeContent.ContactContent) -> Unit,
    onConnectToWifi: (QRCodeContent.WiFiContent) -> Unit,
) {
    // Nav
    val navController = rememberAnimatedNavController()

    // Set system icons color
    SystemIconsColor(
        statusBarColor = SCAppTheme.colors.transparent,
        statusBarDarkIcons = false,
        navigationBarColor = SCAppTheme.colors.transparent,
        navigationBarDarkIcons = false
    )

    ScannyNavHost(
        navHostController = navController,
        onAddToContact = onAddToContact,
        onConnectToWifi = onConnectToWifi,
    )
}