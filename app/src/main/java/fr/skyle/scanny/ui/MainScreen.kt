package fr.skyle.scanny.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.*
import androidx.compose.runtime.*
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import fr.skyle.scanny.navigation.ScannyNavHost
import fr.skyle.scanny.utils.qrCode.QRCodeContent


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen(
    onAddToContact: (QRCodeContent.ContactContent) -> Unit
) {
    // Nav
    val navController = rememberAnimatedNavController()

    ScannyNavHost(
        navHostController = navController,
        onAddToContact = onAddToContact
    )
}