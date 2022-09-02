package fr.skyle.scanny.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.os.bundleOf
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import fr.skyle.scanny.R
import fr.skyle.scanny.SCREEN_TIME_TRANSITION
import fr.skyle.scanny.enums.QRType
import fr.skyle.scanny.ext.QRCodeContent
import fr.skyle.scanny.ext.getParcelable
import fr.skyle.scanny.ext.navigate
import fr.skyle.scanny.ui.createQRText.CreateQRTextScreen
import fr.skyle.scanny.ui.createQRUrlScreen.CreateQRUrlScreen
import fr.skyle.scanny.ui.generateQR.GenerateQRScreen
import fr.skyle.scanny.ui.generator.GeneratorScreen
import fr.skyle.scanny.ui.history.HistoryScreen
import fr.skyle.scanny.ui.scan.ScanScreen
import fr.skyle.scanny.ui.settings.SettingsScreen
import fr.skyle.scanny.ui.splash.SplashScreen

// --- Main Routes
// -------------------------------------------

object Destination {
    const val SCAN = "scan"
    const val GENERATOR = "generator"
    const val SCAN_HISTORY = "scanHistory"
    const val SETTINGS = "settings"

    const val SPLASH = "splash"
    const val CREATE_QR_TEXT = "createQRText"
    const val CREATE_QR_URL = "createQRUrl"
    const val GENERATE_QR = "generateQR"
}

val screensWithBottomAppBar: MutableList<String> =
    mutableListOf(Destination.SCAN, Destination.SCAN_HISTORY, Destination.GENERATOR, Destination.SETTINGS)

// --- Arguments
// -------------------------------------------

const val ARG_QR_CODE_CONTENT = "ARG_QR_CODE_CONTENT"

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ScannyNavHost(
    navHostController: NavHostController,
    innerPadding: PaddingValues
) {
    AnimatedNavHost(
        navController = navHostController,
        startDestination = Destination.SPLASH,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        composable(route = Destination.SPLASH) {
            SplashScreen(goToMainScreen = {
                navHostController.navigate(route = BottomBarScreens.QRScan.route) {
                    popUpTo(Destination.SPLASH) {
                        inclusive = true
                    }
                }
            })
        }
        composable(
            route = BottomBarScreens.QRScan.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Right, animationSpec = tween(SCREEN_TIME_TRANSITION)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Left, animationSpec = tween(SCREEN_TIME_TRANSITION)
                )
            }
        ) {
            ScanScreen()
        }
        composable(
            route = BottomBarScreens.QRHistory.route,
            enterTransition = {
                when (initialState.destination.route) {
                    BottomBarScreens.QRScan.route ->
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Left, animationSpec = tween(SCREEN_TIME_TRANSITION)
                        )
                    BottomBarScreens.Settings.route, BottomBarScreens.QRGenerator.route ->
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Right, animationSpec = tween(SCREEN_TIME_TRANSITION)
                        )
                    else -> fadeIn(animationSpec = tween(SCREEN_TIME_TRANSITION))
                }
            }, exitTransition = {
                when (targetState.destination.route) {
                    BottomBarScreens.QRScan.route ->
                        slideOutOfContainer(
                            AnimatedContentScope.SlideDirection.Right, animationSpec = tween(SCREEN_TIME_TRANSITION)
                        )
                    BottomBarScreens.Settings.route, BottomBarScreens.QRGenerator.route ->
                        slideOutOfContainer(
                            AnimatedContentScope.SlideDirection.Left, animationSpec = tween(SCREEN_TIME_TRANSITION)
                        )
                    else -> fadeOut(animationSpec = tween(SCREEN_TIME_TRANSITION))
                }
            }
        ) {
            HistoryScreen()
        }
        composable(
            route = BottomBarScreens.QRGenerator.route,
            enterTransition = {
                when (initialState.destination.route) {
                    BottomBarScreens.QRScan.route, BottomBarScreens.QRHistory.route ->
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Left, animationSpec = tween(SCREEN_TIME_TRANSITION)
                        )
                    BottomBarScreens.Settings.route ->
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Right, animationSpec = tween(SCREEN_TIME_TRANSITION)
                        )
                    else -> fadeIn(animationSpec = tween(SCREEN_TIME_TRANSITION))
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    BottomBarScreens.QRScan.route, BottomBarScreens.QRHistory.route ->
                        slideOutOfContainer(
                            AnimatedContentScope.SlideDirection.Right, animationSpec = tween(SCREEN_TIME_TRANSITION)
                        )
                    BottomBarScreens.Settings.route ->
                        slideOutOfContainer(
                            AnimatedContentScope.SlideDirection.Left, animationSpec = tween(SCREEN_TIME_TRANSITION)
                        )
                    else -> fadeOut(animationSpec = tween(SCREEN_TIME_TRANSITION))
                }
            }
        ) {
            GeneratorScreen {
                when (it) {
                    QRType.TEXT ->
                        navHostController.navigate(route = Destination.CREATE_QR_TEXT)
                    QRType.CONTACT -> TODO()
                    QRType.URL ->
                        navHostController.navigate(route = Destination.CREATE_QR_URL)
                    QRType.WIFI -> TODO()
                    QRType.EMAIL -> TODO()
                    QRType.SMS -> TODO()
                }
            }
        }
        composable(
            route = BottomBarScreens.Settings.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Left, animationSpec = tween(SCREEN_TIME_TRANSITION)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Right, animationSpec = tween(SCREEN_TIME_TRANSITION)
                )
            }
        ) {
            SettingsScreen()
        }
        composable(
            route = Destination.CREATE_QR_TEXT,
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Up, animationSpec = tween(SCREEN_TIME_TRANSITION))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Down, animationSpec = tween(SCREEN_TIME_TRANSITION))
            }
        ) {
            CreateQRTextScreen(
                goBackToQRGenerator = {
                    navHostController.popBackStack(route = BottomBarScreens.QRGenerator.route, inclusive = false)
                }, goToCustomQRCode = {
                    navHostController.navigate(Destination.GENERATE_QR, bundleOf(ARG_QR_CODE_CONTENT to it)) {
                        popUpTo(BottomBarScreens.QRGenerator.route) {
                            inclusive = false
                        }
                    }
                }
            )
        }
        composable(
            route = Destination.CREATE_QR_URL,
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Up, animationSpec = tween(SCREEN_TIME_TRANSITION))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Down, animationSpec = tween(SCREEN_TIME_TRANSITION))
            }
        ) {
            CreateQRUrlScreen(
                goBackToQRGenerator = {
                    navHostController.popBackStack(route = BottomBarScreens.QRGenerator.route, inclusive = false)
                }, goToCustomQRCode = {
                    navHostController.navigate(Destination.GENERATE_QR, bundleOf(ARG_QR_CODE_CONTENT to it)) {
                        popUpTo(BottomBarScreens.QRGenerator.route) {
                            inclusive = false
                        }
                    }
                }
            )
        }
        composable(
            route = Destination.GENERATE_QR,
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Up, animationSpec = tween(SCREEN_TIME_TRANSITION))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Down, animationSpec = tween(SCREEN_TIME_TRANSITION))
            }
        ) { navBackStackEntry ->
            // Extract the parcelable argument
            navBackStackEntry.getParcelable(ARG_QR_CODE_CONTENT, QRCodeContent::class.java)?.let { qrCodeContent ->
                GenerateQRScreen(
                    goBackToMain = {
                        navHostController.popBackStack(route = BottomBarScreens.QRGenerator.route, inclusive = false)
                    },
                    qrCodeContent
                )
            }
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
        Destination.SCAN, R.string.home_scan, R.drawable.ic_scan_qr, R.drawable.ic_scan_qr_animated
    )

    object QRHistory : BottomBarScreens(
        Destination.SCAN_HISTORY, R.string.home_history, R.drawable.ic_history_qr, R.drawable.ic_history_qr_animated
    )

    object QRGenerator : BottomBarScreens(
        Destination.GENERATOR, R.string.home_generate, R.drawable.ic_gen_qr, R.drawable.ic_gen_qr_animated
    )

    object Settings : BottomBarScreens(
        Destination.SETTINGS, R.string.home_settings, R.drawable.ic_settings, R.drawable.ic_settings_animated
    )
}