package fr.skyle.scanny.ui.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import fr.skyle.scanny.theme.ScannyTheme


@Composable
fun SplashScreen(
    goToMainScreen: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    // Flow
    val timerFlow by viewModel.splashTimer.collectAsState()

    // Effect
    LaunchedEffect(timerFlow) {
        if (timerFlow) {
            goToMainScreen()
        }
    }
}

@Preview
@Composable
fun PreviewSplashScreen() {
    ScannyTheme {
        SplashScreen({})
    }
}