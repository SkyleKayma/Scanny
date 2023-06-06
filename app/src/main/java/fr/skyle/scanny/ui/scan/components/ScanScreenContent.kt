package fr.skyle.scanny.ui.scan.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.mlkit.vision.barcode.common.Barcode
import fr.skyle.scanny.R
import fr.skyle.scanny.ext.navigateToAppSettings
import fr.skyle.scanny.theme.SCAppTheme
import fr.skyle.scanny.theme.SCTheme
import fr.skyle.scanny.ui.core.SCTopAppBar
import fr.skyle.scanny.ui.scan.components.camera.CameraFilter
import fr.skyle.scanny.ui.scan.components.camera.CameraView

@Composable
fun ScanScreenContent(
    isCameraPermissionGranted: Boolean,
    isFlashEnabled: Boolean,
    onFlashClicked: () -> Unit,
    onGalleryClicked: () -> Unit,
    onCanDetectQRCodeChanged: (Boolean) -> Unit,
    onBarcodeDetected: (Barcode) -> Unit,
    navigateToSettings: () -> Unit,
    navigateToHistory: () -> Unit
) {
    // Context
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SCAppTheme.colors.black)
    ) {
        if (isCameraPermissionGranted) {
            CameraView(
                modifier = Modifier.fillMaxSize(),
                isFlashEnabled = isFlashEnabled,
                onBarcodeDetected = onBarcodeDetected,
                onCanDetectQRCodeChanged = {
                    onCanDetectQRCodeChanged(it)
                }
            )

            CameraFilter(
                modifier = Modifier.fillMaxSize(),
                isFlashEnabled = isFlashEnabled,
                onFlashClicked = onFlashClicked,
                onGalleryClicked = onGalleryClicked
            )

            SCTopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = null,
                backgroundColor = Color.Transparent,
                leftActionContent = {
                    IconButton(
                        onClick = navigateToHistory
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(id = R.drawable.ic_history),
                            contentDescription = null,
                            tint = SCAppTheme.colors.nuance100
                        )
                    }
                },
                rightActionContent = {
                    IconButton(
                        onClick = navigateToSettings
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(id = R.drawable.ic_settings),
                            contentDescription = null,
                            tint = SCAppTheme.colors.nuance100
                        )
                    }
                },
            )
        } else {
            ScanPermissionDenied(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 32.dp),
                navigateToSettings = { context.navigateToAppSettings() }
            )
        }
    }
}

@Preview
@Composable
fun PreviewScanScreenContent() {
    SCTheme {
        ScanScreenContent(
            isCameraPermissionGranted = true,
            isFlashEnabled = true,
            onFlashClicked = {},
            onGalleryClicked = {},
            onCanDetectQRCodeChanged = {},
            onBarcodeDetected = {},
            navigateToSettings = {},
            navigateToHistory = {}
        )
    }
}