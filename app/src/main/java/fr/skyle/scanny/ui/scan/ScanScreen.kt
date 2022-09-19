package fr.skyle.scanny.ui.scan

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import fr.skyle.scanny.R
import fr.skyle.scanny.ext.goToAppSettings
import fr.skyle.scanny.ext.vibrateScan
import fr.skyle.scanny.theme.ScannyTheme
import fr.skyle.scanny.ui.core.ScannyButton
import fr.skyle.scanny.ui.scan.components.CameraFilter
import fr.skyle.scanny.ui.scan.components.CameraView


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ScanScreen(
    viewModel: ScanViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    // Camera permission
    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)

    // Flash
    var hasFlash by remember { mutableStateOf(false) }

    // Camera to use
    var isRearCamera by remember { mutableStateOf(true) }

    // Ask for permission
    LaunchedEffect(key1 = Unit, block = {
        if (!cameraPermissionState.status.isGranted) {
            cameraPermissionState.launchPermissionRequest()
        }
    })

    // BottomSheet
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )

    val scanEvent by viewModel.scanEvent.collectAsState()
    LaunchedEffect(scanEvent) {
        if (scanEvent is ScanViewModel.State.SUCCESS) {
            bottomSheetState.show()
            context.vibrateScan()
        }
    }

    Box {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .background(colorResource(id = R.color.sc_background))
        ) {
            if (cameraPermissionState.status.isGranted) {
                // If granted show camera view
                CameraView(imageAnalysis = viewModel.getImageAnalysis(), hasFlash = hasFlash, isRearCamera = isRearCamera)

                CameraFilter()

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                        .align(Alignment.BottomCenter),
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(onClick = { hasFlash = !hasFlash }) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = if (hasFlash) painterResource(id = R.drawable.ic_flashlight_off) else painterResource(id = R.drawable.ic_flashlight_on),
                            contentDescription = null,
                            tint = MaterialTheme.colors.primary
                        )
                    }

                    Spacer(modifier = Modifier.width(20.dp))

                    IconButton(onClick = { isRearCamera = !isRearCamera }) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            painter = painterResource(id = R.drawable.ic_camera_switch),
                            contentDescription = null,
                            tint = MaterialTheme.colors.primary
                        )
                    }
                }
            } else if (cameraPermissionState.status.shouldShowRationale) {
                // Else show permission status
                SettingsPermission(
                    modifier = Modifier.align(Alignment.Center),
                    textId = R.string.permission_camera_required_message_refused,
                    onClick = {
                        context.goToAppSettings()
                    }
                )
            }
        }
    }

    if (scanEvent is ScanViewModel.State.SUCCESS) {
        ScanResultsModalBottomSheet(
            sheetState = bottomSheetState,
            (scanEvent as ScanViewModel.State.SUCCESS).barcode,
            viewModel = viewModel
        )
    }
}

@Composable
fun SettingsPermission(
    textId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.wrapContentSize()) {
        Text(
            text = stringResource(id = textId),
            style = MaterialTheme.typography.body1,
            color = colorResource(id = R.color.sc_body),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 24.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        ScannyButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.permission_settings),
            onClick = onClick
        )
    }
}

@Preview
@Composable
fun PreviewScanScreen() {
    ScannyTheme {
        ScanScreen()
    }
}