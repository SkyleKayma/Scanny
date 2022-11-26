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
import fr.skyle.scanny.ui.core.buttons.ScannyButton
import fr.skyle.scanny.ui.scan.components.CameraFilter
import fr.skyle.scanny.ui.scan.components.CameraView


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ScanScreen(
    viewModel: ScanViewModel = hiltViewModel()
) {
    // Context
    val context = LocalContext.current

    // Flow
    val scanEvent by viewModel.scanEvent.collectAsState()

    // Remember
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    var hasFlash by remember { mutableStateOf(false) }

    // Effect
    LaunchedEffect(key1 = Unit, block = {
        if (!cameraPermissionState.status.isGranted) {
            cameraPermissionState.launchPermissionRequest()
        }
    })

    LaunchedEffect(scanEvent) {
        if (scanEvent is ScanViewModel.State.SUCCESS) {
            bottomSheetState.show()
            context.vibrateScan()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .background(colorResource(id = R.color.sc_background))
    ) {
        if (cameraPermissionState.status.isGranted) {
            CameraView(imageAnalysis = viewModel.getImageAnalysis(), hasFlash = hasFlash)

            CameraFilter()

            IconButton(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .statusBarsPadding()
                    .padding(12.dp),
                onClick = { hasFlash = !hasFlash }
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = if (hasFlash) painterResource(id = R.drawable.ic_flashlight_off) else painterResource(id = R.drawable.ic_flashlight_on),
                    contentDescription = "",
                    tint = MaterialTheme.colors.primary
                )
            }
        } else if (cameraPermissionState.status.shouldShowRationale) {
            SettingsPermission(
                modifier = Modifier.align(Alignment.Center),
                textId = R.string.permission_camera_required_message_refused,
                onClick = {
                    context.goToAppSettings()
                }
            )
        }

        Row(modifier = Modifier.fillMaxWidth()) {

        }
    }

    if (scanEvent is ScanViewModel.State.SUCCESS) {
        ScanResultsModalBottomSheet(
            sheetState = bottomSheetState,
            barcode = (scanEvent as ScanViewModel.State.SUCCESS).barcode,
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
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 24.dp),
            text = stringResource(id = textId),
            style = MaterialTheme.typography.body1,
            color = colorResource(id = R.color.sc_body),
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