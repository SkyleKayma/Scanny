package fr.skyle.scanny.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import fr.skyle.scanny.R
import fr.skyle.scanny.navigation.ScannyNavHost


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen() {
    // Context
    val context = LocalContext.current.applicationContext

    // Nav
    val navController = rememberAnimatedNavController()

    // System UI Controller
    val systemUiController = rememberSystemUiController()

    // Colors of system bars
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color(context.getColor(R.color.sc_transparent)),
            darkIcons = true
        )
    }

    ScannyNavHost(navHostController = navController)
}