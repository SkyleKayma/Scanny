package fr.skyle.scanny.ui.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import fr.skyle.scanny.theme.SCAppTheme
import fr.skyle.scanny.ui.core.SystemIconsColor
import fr.skyle.scanny.ui.settings.components.SettingsScreenContent

@Composable
fun SettingsScreen(
    navigateToFeedback: () -> Unit,
    navigateToDataPrivacy: () -> Unit,
    navigateToRateApp: () -> Unit,
    navigateToAbout: () -> Unit,
    navigateToOpenium: () -> Unit,
    navigateBack: () -> Unit,
    viewModel: SettingsScreenViewModel = hiltViewModel()
) {
    // Set system icons color
    SystemIconsColor(
        statusBarDarkIcons = true,
        navigationBarDarkIcons = true,
        navigationBarColor = SCAppTheme.colors.transparent
    )

    // Flow
    val isVibrationAfterScanEnabled by viewModel.isVibrationAfterScanEnabled.collectAsState()
    val isOpenLinkAfterScanEnabled by viewModel.isOpenLinkAfterScanEnabled.collectAsState()

    SettingsScreenContent(
        navigateToFeedback = navigateToFeedback,
        navigateToDataPrivacy = navigateToDataPrivacy,
        navigateToRateApp = navigateToRateApp,
        navigateToAbout = navigateToAbout,
        navigateToOpenium = navigateToOpenium,
        navigateBack = navigateBack,
        onVibrationAfterScanChanged = { isEnabled ->
            viewModel.isVibrationAfterScanEnabled(isEnabled)
        },
        onOpenLinkAfterScanChanged = { isEnabled ->
            viewModel.isOpenLinkAfterScanEnabled(isEnabled)
        },
        isVibrateAfterScanEnabled = isVibrationAfterScanEnabled,
        isOpenLinkAfterScanEnabled = isOpenLinkAfterScanEnabled
    )
}