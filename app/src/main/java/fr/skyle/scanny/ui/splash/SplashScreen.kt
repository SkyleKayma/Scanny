package fr.skyle.scanny.ui.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.skyle.scanny.theme.SCTheme


@Composable
fun SplashScreen(
    goToScan: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    // Flow
    val timerFlow by viewModel.splashTimer.collectAsStateWithLifecycle()

    // Effect
    LaunchedEffect(timerFlow) {
        if (timerFlow) {
            goToScan()
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