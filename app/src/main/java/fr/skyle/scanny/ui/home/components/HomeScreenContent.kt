package fr.skyle.scanny.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.skyle.scanny.theme.SCAppTheme
import fr.skyle.scanny.theme.SCTheme
import fr.skyle.scanny.ui.home.components.camera.CameraView

@Composable
fun HomeScreenContent(
    isCameraPermissionGranted: Boolean,
    isFlashEnabled: Boolean,
    onFlashClicked: () -> Unit,
    onGalleryClicked: () -> Unit,
    navigateToAppSettings: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SCAppTheme.colors.backgroundBlack)
    ) {
        if (isCameraPermissionGranted) {
            CameraView(
                modifier = Modifier.fillMaxSize(),
                isFlashEnabled = isFlashEnabled
            )

            CameraFilter(
                modifier = Modifier.fillMaxSize(),
                isFlashEnabled = isFlashEnabled,
                onFlashClicked = onFlashClicked,
                onGalleryClicked = onGalleryClicked
            )
        } else {
            SettingsPermission(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 32.dp),
                navigateToSettings = navigateToAppSettings
            )
        }
    }
}

@Preview
@Composable
fun PreviewHomeScreenContent() {
    SCTheme {
        HomeScreenContent(
            isCameraPermissionGranted = true,
            isFlashEnabled = true,
            onFlashClicked = {},
            onGalleryClicked = {},
            navigateToAppSettings = {}
        )
    }
}