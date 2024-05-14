package fr.skyle.scanny.ui.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fr.skyle.scanny.nav.TemplateArgs
import fr.skyle.scanny.nav.destination.DestScan
import fr.skyle.scanny.nav.destination.DestSplash
import fr.skyle.scanny.ui.scan.ScanScreen
import fr.skyle.scanny.ui.splash.SplashScreen

@Composable
fun ScannyNavHost(
    navHostController: NavHostController
) {
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navHostController,
        startDestination = DestScan.route
    ) {
        composable(route = DestSplash.route) {
            SplashScreen(
                goToScan = {
                    navHostController.navigate(DestScan.createRoute())
                }
            )
        }

        composable(route = DestScan.route) {
            ScanScreen()
        }
    }
}