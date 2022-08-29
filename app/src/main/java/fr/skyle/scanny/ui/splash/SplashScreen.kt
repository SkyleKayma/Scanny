package fr.skyle.scanny.ui.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import fr.skyle.scanny.theme.ScannyTheme
import fr.skyle.scanny.ui.settings.SettingsScreen


@Composable
fun SplashScreen(
    goToMainScreen: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val timerFlow by viewModel.splashTimer.collectAsState()

    LaunchedEffect(timerFlow) {
        if (timerFlow) {
            goToMainScreen()
        }
    }

//    Box {
//
//    }
}

@Preview
@Composable
fun PreviewSettingsScreen() {
    ScannyTheme {
        SettingsScreen()
    }
}