package fr.skyle.scanny.ui.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import fr.skyle.scanny.navigation.splashRoute
import fr.skyle.scanny.ui.destinations.ScanScreenDestination


@RootNavGraph(start = true)
@Destination
@Composable
fun SplashScreen(
    navigator: DestinationsNavigator
) {
    Box {
        navigator.navigate(
            ScanScreenDestination,
            builder = { popUpTo(splashRoute) { inclusive = true } }
        )
    }
}