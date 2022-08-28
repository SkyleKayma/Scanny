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
import androidx.core.os.bundleOf
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import fr.skyle.scanny.R
import fr.skyle.scanny.SCREEN_TIME_TRANSITION
import fr.skyle.scanny.ext.QRCodeContent
import fr.skyle.scanny.ext.getParcelable
import fr.skyle.scanny.ext.navigate
import fr.skyle.scanny.ext.putParcelable
import fr.skyle.scanny.ui.createQRText.CreateQRTextScreen
import fr.skyle.scanny.ui.generateQR.GenerateQRScreen
import fr.skyle.scanny.ui.generator.GeneratorScreen
import fr.skyle.scanny.ui.history.HistoryScreen
import fr.skyle.scanny.ui.scan.ScanScreen
import fr.skyle.scanny.ui.settings.SettingsScreen
import fr.skyle.scanny.ui.splash.SplashScreen

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

val screensWithBottomAppBar = mutableListOf(scanRoute, scanHistoryRoute, generatorRoute, settingsRoute)

// --- Other routes
// -------------------------------------------

// Create QR Text route
const val createQRTextRoute = "createQRText"

// Generate QR route
const val generateQRRoute = "generateQR"
const val ARG_QR_CODE_CONTENT = "ARG_QR_CODE_CONTENT"

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ScannyNavHost(
    navHostController: NavHostController,
    innerPadding: PaddingValues
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
                navHostController.navigate(route = createQRTextRoute)
            }
        }
        composable(route = BottomBarScreens.QRHistory.route) {
            HistoryScreen()
        }
        composable(route = BottomBarScreens.Settings.route) {
            SettingsScreen()
        }
        composable(route = createQRTextRoute,
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Up, animationSpec = tween(SCREEN_TIME_TRANSITION))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Down, animationSpec = tween(SCREEN_TIME_TRANSITION))
            }
        ) { navBackStackEntry ->
            CreateQRTextScreen(
                goBackToMain = {
                    navHostController.popBackStack(route = BottomBarScreens.QRGenerator.route, inclusive = false)
                }, goToCustomQRCode = {
                    // Add parcelable like this cause we can't by classic arguments usage
                    navBackStackEntry.putParcelable(ARG_QR_CODE_CONTENT, it)

                    navHostController.navigate("$generateQRRoute/test", bundleOf(ARG_QR_CODE_CONTENT to it))
                }
            )
        }
        composable(route = "${generateQRRoute}/{testArg}",
            arguments = listOf(
                navArgument("testArg") { type = NavType.StringType },
            ),
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Up, animationSpec = tween(SCREEN_TIME_TRANSITION))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Down, animationSpec = tween(SCREEN_TIME_TRANSITION))
            }
        ) { navBackStackEntry ->
            // Extract the parcelable argument
            val qrCodeContent = navBackStackEntry.getParcelable(ARG_QR_CODE_CONTENT, QRCodeContent::class.java)

            GenerateQRScreen(
                goBackToMain = {
                    navHostController.popBackStack(route = BottomBarScreens.QRGenerator.route, inclusive = false)
                },
                qrCodeContent
            )
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