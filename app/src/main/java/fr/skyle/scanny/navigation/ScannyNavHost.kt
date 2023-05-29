package fr.skyle.scanny.navigation

import android.content.Context
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import fr.skyle.scanny.ext.findActivity
import fr.skyle.scanny.ui.about.AboutScreen
import fr.skyle.scanny.ui.feedback.FeedbackScreen
import fr.skyle.scanny.ui.scan.ScanScreen
import fr.skyle.scanny.ui.settings.SettingsScreen
import fr.skyle.scanny.ui.splash.SplashScreen
import fr.skyle.scanny.utils.qrCode.QRCodeContent

// --- Routes
// -------------------------------------------

object Route {
    const val SPLASH = "splash"
    const val SCAN = "scan"
    const val SETTINGS = "settings"
    const val ABOUT = "about"
    const val FEEDBACK = "feedback"
    const val SCAN_HISTORY = "scanHistory"
    const val GENERATE_QR_LIST = "generateQRList"
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
    onAddToContact: (QRCodeContent.ContactContent) -> Unit,
) {
    // Context
    val context = LocalContext.current

    AnimatedNavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navHostController,
        startDestination = Route.SPLASH
    ) {
        composable(route = Route.SPLASH) {
            SplashScreen(
                goToScan = {
                    navHostController.navigate(route = Route.SCAN) {
                        popUpTo(Route.SPLASH) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = Route.SCAN) {
            ScanScreen(
                navigateToSettings = {
                    navHostController.navigate(Route.SETTINGS)
                },
                onAddToContact = onAddToContact
            )
        }
        composable(route = Route.SETTINGS) {
            SettingsScreen(
                navigateToFeedback = {
                    navHostController.navigate(Route.FEEDBACK)
                },
                navigateToAbout = {
                    navHostController.navigate(Route.ABOUT)
                },
                navigateBack = {
                    navHostController.popBackOrExit(context)
                }
            )
        }
        composable(route = Route.ABOUT) {
            AboutScreen(
                navigateBack = {
                    navHostController.popBackOrExit(context)
                }
            )
        }
        composable(route = Route.FEEDBACK) {
            FeedbackScreen(
                navigateBack = {
                    navHostController.popBackOrExit(context)
                }
            )
        }
//        composable(route = Destination.GENERATE_QR_LIST) {
//            GenerateQRListScreen(
//                goToCreateQRScreen = {
//                    navHostController.navigate(route = "${Destination.CREATE_QR}/${it.name}")
//                }
//            )
//        }
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

private fun NavHostController.popBackOrExit(composableContext: Context) {
    if (!popBackStack()) {
        val activity = composableContext.findActivity()
        activity.finish()
    }
}