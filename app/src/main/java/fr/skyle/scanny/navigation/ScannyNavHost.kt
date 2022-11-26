package fr.skyle.scanny.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.os.bundleOf
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import fr.skyle.scanny.R
import fr.skyle.scanny.SCREEN_TIME_TRANSITION
import fr.skyle.scanny.enums.QRType
import fr.skyle.scanny.ext.getParcelable
import fr.skyle.scanny.ext.navigate
import fr.skyle.scanny.ui.createQR.CreateQRScreen
import fr.skyle.scanny.ui.generateQR.GenerateQRScreen
import fr.skyle.scanny.ui.generateQRList.GenerateQRListScreen
import fr.skyle.scanny.ui.history.HistoryScreen
import fr.skyle.scanny.ui.scan.ScanScreen
import fr.skyle.scanny.ui.settings.SettingsScreen
import fr.skyle.scanny.ui.splash.SplashScreen
import fr.skyle.scanny.utils.qrCode.QRCodeContent

// --- Routes
// -------------------------------------------

object Destination {
    const val SCAN = "scan"
    const val SCAN_HISTORY = "scanHistory"
    const val GENERATE_QR_LIST = "generateQRList"
    const val SETTINGS = "settings"

    const val SPLASH = "splash"
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
    navHostController: NavHostController
) {
    val context = LocalContext.current

    AnimatedNavHost(
        navController = navHostController,
        startDestination = Destination.SPLASH,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(route = Destination.SPLASH) {
            SplashScreen(
                goToMainScreen = {
                    navHostController.navigate(route = Destination.SCAN) {
                        popUpTo(Destination.SPLASH) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = Destination.SCAN) {
            ScanScreen()
        }
        composable(route = Destination.SCAN_HISTORY) {
            HistoryScreen()
        }
        composable(route = Destination.GENERATE_QR_LIST) {
            GenerateQRListScreen(
                goToCreateQRScreen = {
                    navHostController.navigate(route = "${Destination.CREATE_QR}/${it.name}")
                }
            )
        }
        composable(route = Destination.SETTINGS) {
            SettingsScreen(
                goToNotifications = {

                }, goToFeedback = {

                }, goToRateTheApp = {
                    showRateTheAppScreen(context)
                }, goToDataPrivacy = {
                    showDataPrivacy(context)
                }, goToAbout = {

                }
            )
        }
        composable(
            route = "${Destination.CREATE_QR}/{${Argument.QR_TYPE}}",
            arguments = listOf(
                navArgument(Argument.QR_TYPE) { type = NavType.StringType }
            ),
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Up, animationSpec = tween(SCREEN_TIME_TRANSITION))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Down, animationSpec = tween(SCREEN_TIME_TRANSITION))
            }
        ) {
            val qrTypeString = it.arguments?.getString(Argument.QR_TYPE, QRType.TEXT.name) ?: QRType.TEXT.name
            val qrType = QRType.values().first { it.name == qrTypeString }

            CreateQRScreen(
                qrType = qrType,
                goBackToGenerateQRList = {
                    navHostController.popBackStack(route = Destination.GENERATE_QR_LIST, inclusive = false)
                }, goToGenerateQRCode = {
                    navHostController.navigate(Destination.GENERATE_QR, bundleOf(ARG_QR_CODE_CONTENT to it)) {
                        popUpTo(Destination.GENERATE_QR_LIST) {
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
                        navHostController.popBackStack(route = Destination.GENERATE_QR_LIST, inclusive = false)
                    },
                    qrCodeContent
                )
            }
        }
    }
}

fun showDataPrivacy(context: Context) {
    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(context.getString(R.string.app_data_privacy_url))))
}

fun showRateTheAppScreen(context: Context) {
    val marketIntent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=${context.packageName}")).apply {
        addFlags(
            Intent.FLAG_ACTIVITY_NO_HISTORY or
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        )
    }

    if (marketIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(marketIntent)
    } else context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=${context.packageName}")))
}