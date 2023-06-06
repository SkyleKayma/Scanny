package fr.skyle.scanny.ui.barcodeDetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.skyle.scanny.ui.barcodeDetail.components.BarcodeDetailScreenContent

@Composable
fun BarcodeDetailScreen(
    navigateBack: () -> Unit,
    viewModel: BarcodeDetailScreenViewModel = hiltViewModel()
) {
    // Flow
    val barcode by viewModel.barcode.collectAsStateWithLifecycle()

    BarcodeDetailScreenContent(
        barcode = { barcode },
        navigateBack = navigateBack
    )
}