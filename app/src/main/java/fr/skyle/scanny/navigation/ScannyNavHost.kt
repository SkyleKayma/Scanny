package fr.skyle.scanny.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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

val screensWithBottomAppBar: MutableList<String> =
    mutableListOf(Destination.SCAN, Destination.SCAN_HISTORY, Destination.GENERATE_QR_LIST, Destination.SETTINGS)

sealed class BottomBarScreens(
    val route: String,
    @StringRes val resourceId: Int,
    @DrawableRes val iconId: Int,
    @DrawableRes val animatedIconId: Int
) {
    object Scan : BottomBarScreens(
        Destination.SCAN, R.string.home_scan, R.drawable.ic_scan_qr, R.drawable.ic_scan_qr_animated
    )

    object ScanHistory : BottomBarScreens(
        Destination.SCAN_HISTORY, R.string.home_history, R.drawable.ic_history_qr, R.drawable.ic_history_qr_animated
    )

    object GenerateQRList : BottomBarScreens(
        Destination.GENERATE_QR_LIST, R.string.home_generate, R.drawable.ic_gen_qr, R.drawable.ic_gen_qr_animated
    )

    object Settings : BottomBarScreens(
        Destination.SETTINGS, R.string.home_settings, R.drawable.ic_settings, R.drawable.ic_settings_animated
    )
}

// --- Arguments
// -------------------------------------------

const val ARG_QR_CODE_CONTENT = "ARG_QR_CODE_CONTENT"

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ScannyNavHost(
    navHostController: NavHostController,
    innerPadding: PaddingValues
) {
    val context = LocalContext.current

    AnimatedNavHost(
        navController = navHostController,
        startDestination = Destination.SPLASH,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        composable(route = Destination.SPLASH) {
            SplashScreen(goToMainScreen = {
                navHostController.navigate(route = BottomBarScreens.Scan.route) {
                    popUpTo(Destination.SPLASH) {
                        inclusive = true
                    }
                }
            })
        }
        composable(
            route = BottomBarScreens.Scan.route,
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
            route = BottomBarScreens.ScanHistory.route,
            enterTransition = {
                when (initialState.destination.route) {
                    BottomBarScreens.Scan.route ->
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Left, animationSpec = tween(SCREEN_TIME_TRANSITION)
                        )
                    BottomBarScreens.Settings.route, BottomBarScreens.GenerateQRList.route ->
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Right, animationSpec = tween(SCREEN_TIME_TRANSITION)
                        )
                    else -> fadeIn(animationSpec = tween(SCREEN_TIME_TRANSITION))
                }
            }, exitTransition = {
                when (targetState.destination.route) {
                    BottomBarScreens.Scan.route ->
                        slideOutOfContainer(
                            AnimatedContentScope.SlideDirection.Right, animationSpec = tween(SCREEN_TIME_TRANSITION)
                        )
                    BottomBarScreens.Settings.route, BottomBarScreens.GenerateQRList.route ->
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
            route = BottomBarScreens.GenerateQRList.route,
            enterTransition = {
                when (initialState.destination.route) {
                    BottomBarScreens.Scan.route, BottomBarScreens.ScanHistory.route ->
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
                    BottomBarScreens.Scan.route, BottomBarScreens.ScanHistory.route ->
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
            GenerateQRListScreen(
                goToCreateQRScreen = {
                    navHostController.navigate(route = "${Destination.CREATE_QR}/${it.name}")
                }
            )
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
                    navHostController.popBackStack(route = BottomBarScreens.GenerateQRList.route, inclusive = false)
                }, goToGenerateQRCode = {
                    navHostController.navigate(Destination.GENERATE_QR, bundleOf(ARG_QR_CODE_CONTENT to it)) {
                        popUpTo(BottomBarScreens.GenerateQRList.route) {
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
                        navHostController.popBackStack(route = BottomBarScreens.GenerateQRList.route, inclusive = false)
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