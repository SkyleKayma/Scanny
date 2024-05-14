package fr.skyle.scanny.ui.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fr.skyle.scanny.nav.destination.DestScan
import fr.skyle.scanny.ui.scan.ScanRoute

@Composable
fun ScannyNavHost(
    navHostController: NavHostController
) {
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navHostController,
        startDestination = DestScan.route
    ) {
        composable(route = DestScan.route) {
            ScanRoute()
        }
    }
}