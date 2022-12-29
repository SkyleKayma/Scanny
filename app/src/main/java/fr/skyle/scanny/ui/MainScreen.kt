package fr.skyle.scanny.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import fr.skyle.scanny.enums.ModalType
import fr.skyle.scanny.events.modalTypeEvent
import fr.skyle.scanny.ext.vibrateScan
import fr.skyle.scanny.navigation.ScannyNavHost
import fr.skyle.scanny.theme.SCAppTheme
import fr.skyle.scanny.ui.core.SystemIconsColor
import fr.skyle.scanny.ui.home.components.ScanSuccessBottomSheet
import fr.skyle.scanny.utils.scan.BarCodeHelper
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen(
    navigateToDataPrivacy: () -> Unit,
    navigateToRateApp: () -> Unit,
    navigateToAppSettings: () -> Unit,
    navigateToOpenium: () -> Unit
) {
    // Context
    val context = LocalContext.current

    // Nav
    val navController = rememberAnimatedNavController()

    // Set system icons color
    SystemIconsColor(statusBarColor = SCAppTheme.colors.transparent)

    // Remember
    val scope = rememberCoroutineScope()
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
            BarCodeHelper.isQRCodeScanEnabled(true)
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
        sheetBackgroundColor = SCAppTheme.colors.background,
        sheetState = modalState,
        sheetContent = { modalContent() }
    ) {
        ScannyNavHost(
            navHostController = navController,
            navigateToDataPrivacy = navigateToDataPrivacy,
            navigateToRateApp = navigateToRateApp,
            navigateToAppSettings = navigateToAppSettings,
            navigateToOpenium = navigateToOpenium
        )
    }
}