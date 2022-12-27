package fr.skyle.scanny.ui.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import fr.skyle.scanny.theme.SCTheme


@Composable
fun SplashScreen(
    goToHome: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    // Flow
    val timerFlow by viewModel.splashTimer.collectAsState()

    // Effect
    LaunchedEffect(timerFlow) {
        if (timerFlow) {
            goToHome()
        }
    }
}

@Preview
@Composable
fun PreviewSplashScreen() {
    SCTheme {
        SplashScreen({})
    }
}