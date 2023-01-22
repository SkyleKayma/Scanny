package fr.skyle.scanny.ui.scan

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import fr.skyle.scanny.theme.SCAppTheme
import fr.skyle.scanny.ui.core.SystemIconsColor
import fr.skyle.scanny.ui.scan.components.HomeScreenContent


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    navigateToAppSettings: () -> Unit,
    navigateToSettings: () -> Unit,
    viewModel: ScanViewModel = hiltViewModel()
) {
    // Set system icons color
    SystemIconsColor(
        statusBarDarkIcons = false,
        navigationBarDarkIcons = false,
        navigationBarColor = SCAppTheme.colors.transparent
    )

    // Remember
    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    var isFlashEnabled by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit, block = {
        if (!cameraPermissionState.status.isGranted) {
            cameraPermissionState.launchPermissionRequest()
        }
    })

    HomeScreenContent(
        isCameraPermissionGranted = cameraPermissionState.status.isGranted,
        isFlashEnabled = isFlashEnabled,
        onFlashClicked = {
            isFlashEnabled = !isFlashEnabled
        },
        onGalleryClicked = {
            // TODO
        },
        navigateToAppSettings = navigateToAppSettings,
        navigateToSettings = navigateToSettings
    )
}