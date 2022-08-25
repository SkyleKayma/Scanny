package fr.skyle.scanny.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.systemuicontroller.SystemUiController
import fr.skyle.scanny.R
import fr.skyle.scanny.ui.generateQRText.GenerateQRTextScreen
import fr.skyle.scanny.ui.generator.GeneratorScreen
import fr.skyle.scanny.ui.history.HistoryScreen
import fr.skyle.scanny.ui.main.MainViewModel
import fr.skyle.scanny.ui.scan.ScanScreen
import fr.skyle.scanny.ui.settings.SettingsScreen
import fr.skyle.scanny.ui.splash.SplashScreen

// Splash
const val splashRoute = "splash"

// Main routes
const val scanRoute = "scan"
const val generatorRoute = "generator"
const val scanHistoryRoute = "history"
const val settingsRoute = "settings"

val screensWithBottomAppBar = mutableListOf(scanRoute, scanHistoryRoute, generatorRoute, settingsRoute)

// Other routes
const val generateQRTextRoute = "generateQRText"

const val TIME_ANIMATION = 250

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ScannyNavHost(
    systemUiController: SystemUiController,
    navHostController: NavHostController,
    innerPadding: PaddingValues,
    mainViewModel: MainViewModel
) {
    AnimatedNavHost(
        navController = navHostController,
        startDestination = splashRoute,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        composable(route = splashRoute) {
            SplashScreen {
                navHostController.navigate(route = BottomBarScreens.QRScan.route) {
                    popUpTo(splashRoute) {
                        inclusive = true
                    }
                }
            }
        }
        composable(route = BottomBarScreens.QRScan.route) {
            ScanScreen()
        }
        composable(route = BottomBarScreens.QRGenerator.route) {
            GeneratorScreen {
                navHostController.navigate(route = generateQRTextRoute)
            }
        }
        composable(route = BottomBarScreens.QRHistory.route) {
            HistoryScreen()
        }
        composable(route = BottomBarScreens.Settings.route) {
            SettingsScreen()
        }
        composable(route = generateQRTextRoute,
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Up, animationSpec = tween(TIME_ANIMATION))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Down, animationSpec = tween(TIME_ANIMATION))
            }
        ) {
            GenerateQRTextScreen(goBackToMain = {
                navHostController.popBackStack(route = BottomBarScreens.QRGenerator.route, inclusive = false)
            })
        }
    }
}

sealed class BottomBarScreens(
    val route: String,
    @StringRes val resourceId: Int,
    @DrawableRes val iconId: Int,
    @DrawableRes val animatedIconId: Int
) {
    object QRScan : BottomBarScreens(
        scanRoute, R.string.home_scan, R.drawable.ic_scan_qr, R.drawable.ic_scan_qr_animated
    )

    object QRHistory : BottomBarScreens(
        scanHistoryRoute, R.string.home_history, R.drawable.ic_history_qr, R.drawable.ic_history_qr_animated
    )

    object QRGenerator : BottomBarScreens(
        generatorRoute, R.string.home_generate, R.drawable.ic_gen_qr, R.drawable.ic_gen_qr_animated
    )

    object Settings : BottomBarScreens(
        settingsRoute, R.string.home_settings, R.drawable.ic_settings, R.drawable.ic_settings_animated
    )
}