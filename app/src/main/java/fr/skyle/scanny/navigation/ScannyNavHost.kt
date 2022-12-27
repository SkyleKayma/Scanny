package fr.skyle.scanny.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import fr.skyle.scanny.ui.home.HomeScreen
import fr.skyle.scanny.ui.splash.SplashScreen

// --- Routes
// -------------------------------------------

object Route {
    const val SPLASH = "splash"

    const val HOME = "home"

    const val SCAN_HISTORY = "scanHistory"
    const val GENERATE_QR_LIST = "generateQRList"
    const val SETTINGS = "settings"
    const val CREATE_QR = "createQR"
    const val GENERATE_QR = "generateQR"
}

object Argument {
    const val QR_TYPE = "QrType"
}

// --- Arguments
// -------------------------------------------

const val ARG_QR_CODE_CONTENT = "ARG_QR_CODE_CONTENT"

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ScannyNavHost(
    navHostController: NavHostController,
    onShowDataPrivacy: () -> Unit,
    onShowRateTheAppScreen: () -> Unit,
    onGoToAppSettings: () -> Unit
) {
    AnimatedNavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navHostController,
        startDestination = Route.SPLASH
    ) {
        composable(route = Route.SPLASH) {
            SplashScreen(
                goToHome = {
                    navHostController.navigate(route = Route.HOME) {
                        popUpTo(Route.SPLASH) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = Route.HOME) {
            HomeScreen(
                navigateToAppSettings = onGoToAppSettings
            )
        }
//        composable(route = Destination.SCAN_HISTORY) {
//            HistoryScreen()
//        }
//        composable(route = Destination.GENERATE_QR_LIST) {
//            GenerateQRListScreen(
//                goToCreateQRScreen = {
//                    navHostController.navigate(route = "${Destination.CREATE_QR}/${it.name}")
//                }
//            )
//        }
//        composable(route = Destination.SETTINGS) {
//            SettingsScreen(
//                goToNotifications = {},
//                goToFeedback = {},
//                goToDataPrivacy = onShowDataPrivacy,
//                goToRateTheApp = onShowRateTheAppScreen,
//                goToAbout = {}
//            )
//        }
//        composable(
//            route = "${Destination.CREATE_QR}/{${Argument.QR_TYPE}}",
//            arguments = listOf(
//                navArgument(Argument.QR_TYPE) { type = NavType.StringType }
//            )
//        ) {
//            val qrTypeString = it.arguments?.getString(Argument.QR_TYPE, QRType.TEXT.name) ?: QRType.TEXT.name
//            val qrType = QRType.values().first { it.name == qrTypeString }
//
//            CreateQRScreen(
//                qrType = qrType,
//                goBackToGenerateQRList = {
//                    navHostController.popBackStack(route = Destination.GENERATE_QR_LIST, inclusive = false)
//                }, goToGenerateQRCode = {
//                    navHostController.navigate(Destination.GENERATE_QR, bundleOf(ARG_QR_CODE_CONTENT to it)) {
//                        popUpTo(Destination.GENERATE_QR_LIST) {
//                            inclusive = false
//                        }
//                    }
//                }
//            )
//        }
//        composable(
//            route = Destination.GENERATE_QR
//        ) { navBackStackEntry ->
//            // Extract the parcelable argument
//            navBackStackEntry.getParcelable(ARG_QR_CODE_CONTENT, QRCodeContent::class.java)?.let { qrCodeContent ->
//                GenerateQRScreen(
//                    goBackToMain = {
//                        navHostController.popBackStack(route = Destination.GENERATE_QR_LIST, inclusive = false)
//                    },
//                    qrCodeContent = qrCodeContent
//                )
//            }
//        }
    }
}