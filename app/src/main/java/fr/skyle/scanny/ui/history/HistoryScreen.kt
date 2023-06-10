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
    val state by viewModel.state.collectAsStateWithLifecycle()

    HistoryScreenContent(
        state = { state },
        navigateToBarcodeDetail = navigateToBarcodeDetail,
        navigateBack = navigateBack
    )
}