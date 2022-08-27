package fr.skyle.scanny.ui.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable


@Composable
fun SplashScreen(
    goToMainScreen: () -> Unit
) {
    Box {
        goToMainScreen()
    }
}