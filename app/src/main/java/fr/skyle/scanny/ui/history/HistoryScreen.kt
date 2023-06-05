package fr.skyle.scanny.ui.history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.skyle.scanny.ui.history.components.HistoryScreenContent

@Composable
fun HistoryScreen(
    navigateToBarcodeDetail: (Long) -> Unit,
    navigateBack: () -> Unit,
    viewModel: HistoryScreenViewModel = hiltViewModel()
) {
    // Flow
    val barcodes by viewModel.barcodes.collectAsStateWithLifecycle()

    HistoryScreenContent(
        barcodes = { barcodes },
        navigateToBarcodeDetail = navigateToBarcodeDetail,
        navigateBack = navigateBack
    )
}