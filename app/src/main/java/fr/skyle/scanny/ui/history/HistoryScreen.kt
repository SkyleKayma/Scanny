package fr.skyle.scanny.ui.history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import fr.skyle.scanny.ui.history.components.HistoryScreenContent

@Composable
fun HistoryScreen(
    navigateBack: () -> Unit,
    viewModel: HistoryScreenViewModel = hiltViewModel()
) {
    // Flow
    val barcodes by viewModel.barcodes.collectAsState()

    HistoryScreenContent(
        barcodes = { barcodes },
        navigateBack = navigateBack
    )
}