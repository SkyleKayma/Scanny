package fr.skyle.scanny.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import fr.skyle.scanny.navigation.ScannyNavHost


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen(
    onShowDataPrivacy: () -> Unit,
    onShowRateTheAppScreen: () -> Unit,
    onGoToAppSettings: () -> Unit
) {
    // Nav
    val navController = rememberAnimatedNavController()

    // System UI Controller
    val systemUiController = rememberSystemUiController()

    // Colors of system bars
    LaunchedEffect(key1 = Unit, block = {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = true
        )
    })

    ScannyNavHost(
        navHostController = navController,
        onShowDataPrivacy = onShowDataPrivacy,
        onShowRateTheAppScreen = onShowRateTheAppScreen,
        onGoToAppSettings = onGoToAppSettings
    )
}