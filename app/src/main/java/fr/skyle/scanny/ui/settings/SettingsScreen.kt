package fr.skyle.scanny.ui.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.skyle.scanny.theme.SCAppTheme
import fr.skyle.scanny.ui.core.SystemIconsColor
import fr.skyle.scanny.ui.settings.components.SettingsScreenContent

@Composable
fun SettingsScreen(
    navigateToFeedback: () -> Unit,
    navigateToAbout: () -> Unit,
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
    val isVibrationAfterScanEnabled by viewModel.isVibrationAfterScanEnabled.collectAsStateWithLifecycle()
    val isOpenLinkAfterScanEnabled by viewModel.isOpenLinkAfterScanEnabled.collectAsStateWithLifecycle()
    val isRawContentShown by viewModel.isRawContentShown.collectAsStateWithLifecycle()

    SettingsScreenContent(
        navigateToFeedback = navigateToFeedback,
        navigateToAbout = navigateToAbout,
        navigateBack = navigateBack,
        onVibrationAfterScanChanged = { isEnabled ->
            viewModel.isVibrationAfterScanEnabled(isEnabled)
        },
        onOpenLinkAfterScanChanged = { isEnabled ->
            viewModel.isOpenLinkAfterScanEnabled(isEnabled)
        },
        onRawContentShownChanged = { isShown ->
            viewModel.isRawContentShown(isShown)
        },
        isVibrateAfterScanEnabled = { isVibrationAfterScanEnabled },
        isOpenLinkAfterScanEnabled = { isOpenLinkAfterScanEnabled },
        isRawContentShown = { isRawContentShown }
    )
}