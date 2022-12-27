package fr.skyle.scanny.ui.home

import android.Manifest
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import fr.skyle.scanny.R
import fr.skyle.scanny.enums.ModalType
import fr.skyle.scanny.events.modalTypeEvent
import fr.skyle.scanny.ext.vibrateScan
import fr.skyle.scanny.ui.home.components.HomeScreenContent
import fr.skyle.scanny.ui.home.components.ScanSuccessBottomSheet
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    navigateToAppSettings: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    // Context
    val context = LocalContext.current

    // Remember
    val scope = rememberCoroutineScope()
    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    var isFlashEnabled by remember { mutableStateOf(false) }
    var modalContent: @Composable () -> Unit by remember {
        mutableStateOf({
            Box(modifier = Modifier.fillMaxSize())
        })
    }
    val modalState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    // Back override
    BackHandler(enabled = modalState.isVisible) {
        scope.launch {
            modalState.hide()
        }
    }

    // Effect
    LaunchedEffect(key1 = modalState.currentValue, block = {
        if (!modalState.isVisible) {
            viewModel.isQRCodeScanEnabled(true)
        }
    })

    LaunchedEffect(key1 = Unit, block = {
        if (!cameraPermissionState.status.isGranted) {
            cameraPermissionState.launchPermissionRequest()
        }
    })

    LaunchedEffect(modalState) {
        modalTypeEvent.collectLatest { bottomSheetType ->
            when (bottomSheetType) {
                is ModalType.ScanSuccessModal -> {
                    modalContent = {
                        context.vibrateScan()
                        ScanSuccessBottomSheet(barcode = bottomSheetType.barcode)
                    }
                    modalState.show()
                }
                null -> modalState.hide()
            }
        }
    }

    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        sheetBackgroundColor = colorResource(id = R.color.sc_background),
        sheetState = modalState,
        sheetContent = { modalContent() }
    ) {
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
    }
}