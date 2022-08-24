package fr.skyle.scanny.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import fr.skyle.scanny.R
import fr.skyle.scanny.SCREEN_TIME_TRANSITION
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

val screensWithBottomAppBar = mutableListOf(scanRoute, generatorRoute, scanHistoryRoute, settingsRoute)

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ScannyNavHost(
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
        composable(
            route = splashRoute,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            SplashScreen {
                navHostController.navigate(route = BottomBarScreens.QRScan.route) {
                    popUpTo(splashRoute) {
                        inclusive = true
                    }
                }
            }
        }
        composable(
            route = BottomBarScreens.QRScan.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(SCREEN_TIME_TRANSITION, easing = LinearEasing)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(SCREEN_TIME_TRANSITION, easing = LinearEasing)
                )
            }
        ) {
            ScanScreen()
        }
        composable(
            route = BottomBarScreens.QRGenerator.route,
            enterTransition = {
                when (initialState.destination.route) {
                    BottomBarScreens.QRScan.route ->
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Left,
                            animationSpec = tween(SCREEN_TIME_TRANSITION, easing = LinearEasing)
                        )
                    BottomBarScreens.QRHistory.route, BottomBarScreens.Settings.route ->
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Right,
                            animationSpec = tween(SCREEN_TIME_TRANSITION, easing = LinearEasing)
                        )
                    else -> EnterTransition.None
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    BottomBarScreens.QRScan.route ->
                        slideOutOfContainer(
                            AnimatedContentScope.SlideDirection.Right,
                            animationSpec = tween(SCREEN_TIME_TRANSITION, easing = LinearEasing)
                        )
                    BottomBarScreens.QRHistory.route, BottomBarScreens.Settings.route ->
                        slideOutOfContainer(
                            AnimatedContentScope.SlideDirection.Left,
                            animationSpec = tween(SCREEN_TIME_TRANSITION, easing = LinearEasing)
                        )
                    else -> ExitTransition.None
                }
            }
        ) {
            GeneratorScreen()
        }
        composable(
            route = BottomBarScreens.QRHistory.route,
            enterTransition = {
                when (initialState.destination.route) {
                    BottomBarScreens.QRScan.route, BottomBarScreens.QRGenerator.route ->
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Left,
                            animationSpec = tween(SCREEN_TIME_TRANSITION, easing = LinearEasing)
                        )
                    BottomBarScreens.Settings.route ->
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Right,
                            animationSpec = tween(SCREEN_TIME_TRANSITION, easing = LinearEasing)
                        )
                    else -> EnterTransition.None
                }
            }, exitTransition = {
                when (targetState.destination.route) {
                    BottomBarScreens.QRScan.route, BottomBarScreens.QRGenerator.route ->
                        slideOutOfContainer(
                            AnimatedContentScope.SlideDirection.Right,
                            animationSpec = tween(SCREEN_TIME_TRANSITION, easing = LinearEasing)
                        )
                    BottomBarScreens.Settings.route ->
                        slideOutOfContainer(
                            AnimatedContentScope.SlideDirection.Left,
                            animationSpec = tween(SCREEN_TIME_TRANSITION, easing = LinearEasing)
                        )
                    else -> ExitTransition.None
                }
            }
        ) {
            HistoryScreen()
        }
        composable(
            route = BottomBarScreens.Settings.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(SCREEN_TIME_TRANSITION, easing = LinearEasing)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(SCREEN_TIME_TRANSITION, easing = LinearEasing)
                )
            }
        ) {
            SettingsScreen()
        }
    }
}

sealed class BottomBarScreens(val route: String, @StringRes val resourceId: Int, @DrawableRes val iconId: Int) {
    object QRScan : BottomBarScreens(scanRoute, R.string.home_scan, R.drawable.ic_qrcode_scan)
    object QRGenerator : BottomBarScreens(generatorRoute, R.string.home_generate, R.drawable.ic_qrcode_generator)
    object QRHistory : BottomBarScreens(scanHistoryRoute, R.string.home_history, R.drawable.ic_qrcode_history)
    object Settings : BottomBarScreens(settingsRoute, R.string.home_settings, R.drawable.ic_settings)
}