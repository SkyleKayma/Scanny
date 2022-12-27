package fr.skyle.scanny.ui.home

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import fr.skyle.scanny.ext.vibrateScan
import fr.skyle.scanny.ui.home.components.HomeScreenContent


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    navigateToAppSettings: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    // Context
    val context = LocalContext.current

    // Flow
    val scanEvent by viewModel.scanEvent.collectAsState()

    // Remember
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    var isFlashEnabled by remember { mutableStateOf(false) }

    // Effect
    LaunchedEffect(key1 = Unit, block = {
        if (!cameraPermissionState.status.isGranted) {
            cameraPermissionState.launchPermissionRequest()
        }
    })

    LaunchedEffect(scanEvent) {
        if (scanEvent is HomeViewModel.State.SUCCESS) {
            context.vibrateScan()
            bottomSheetState.show()
        }
    }

    HomeScreenContent(
        isCameraPermissionGranted = cameraPermissionState.status.isGranted,
        imageAnalysis = viewModel.getImageAnalysis(),
        isFlashEnabled = isFlashEnabled,
        onFlashClicked = {
            isFlashEnabled = !isFlashEnabled
        },
        onGalleryClicked = {
            // TODO
        },
        navigateToAppSettings = navigateToAppSettings
    )

    if (scanEvent is HomeViewModel.State.SUCCESS) {
        ScanResultsModalBottomSheet(
            sheetState = bottomSheetState,
            barcode = (scanEvent as HomeViewModel.State.SUCCESS).barcode,
            viewModel = viewModel
        )
    }
}