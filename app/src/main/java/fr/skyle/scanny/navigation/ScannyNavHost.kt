package fr.skyle.scanny.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import fr.skyle.scanny.enums.BarcodeCodeContent
import fr.skyle.scanny.ui.about.AboutScreen
import fr.skyle.scanny.ui.barcodeDetail.BarcodeDetailScreen
import fr.skyle.scanny.ui.feedback.FeedbackScreen
import fr.skyle.scanny.ui.history.HistoryScreen
import fr.skyle.scanny.ui.scan.ScanScreen
import fr.skyle.scanny.ui.settings.SettingsScreen

object Route {
    const val SCAN = "scan"
    const val SETTINGS = "settings"
    const val ABOUT = "about"
    const val FEEDBACK = "feedback"
    const val SCAN_HISTORY = "scanHistory"
    const val BARCODE_DETAIL = "barcodeDetail"
    const val GENERATE_QR_LIST = "generateQRList"
    const val CREATE_QR = "createQR"
    const val GENERATE_QR = "generateQR"
}

object RouteArgument {
    const val ARG_BARCODE_DATA_ID = "ARG_BARCODE_DATA_ID"
}

@Composable
fun ScannyNavHost(
    navHostController: NavHostController,
    onAddToContact: (BarcodeCodeContent.ContactContent) -> Unit,
    onSetWindowBackgroundLight: () -> Unit
) {
    AnimatedNavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navHostController,
        startDestination = Route.SCAN
    ) {
        composable(route = Route.SCAN) {
            ScanScreen(
                navigateToSettings = {
                    navHostController.navigate(Route.SETTINGS)
                },
                navigateToHistory = {
                    navHostController.navigate(Route.SCAN_HISTORY)
                },
                onAddToContact = onAddToContact,
                onSetWindowBackgroundLight = onSetWindowBackgroundLight
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
                    navHostController.popBackStack()
                }
            )
        }

        composable(route = Route.ABOUT) {
            AboutScreen(
                navigateBack = {
                    navHostController.popBackStack()
                }
            )
        }

        composable(route = Route.FEEDBACK) {
            FeedbackScreen(
                navigateBack = {
                    navHostController.popBackStack()
                }
            )
        }

        composable(route = Route.SCAN_HISTORY) {
            HistoryScreen(
                navigateToBarcodeDetail = { barcodeDataId ->
                    navHostController.navigate(route = "${Route.BARCODE_DETAIL}/$barcodeDataId")
                },
                navigateBack = {
                    navHostController.popBackStack()
                }
            )
        }

        composable(
            route = "${Route.BARCODE_DETAIL}/{${RouteArgument.ARG_BARCODE_DATA_ID}}",
            arguments = listOf(
                navArgument(RouteArgument.ARG_BARCODE_DATA_ID) {
                    type = NavType.LongType
                }
            ),
        ) {
            BarcodeDetailScreen(
                navigateBack = {
                    navHostController.popBackStack()
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