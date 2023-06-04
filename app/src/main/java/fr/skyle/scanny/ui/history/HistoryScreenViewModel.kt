package fr.skyle.scanny.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.skyle.scanny.data.repository.BarcodeDataRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HistoryScreenViewModel @Inject constructor(
    private val barcodeDataRepository: BarcodeDataRepository
) : ViewModel() {
    val barcodes by lazy {
        barcodeDataRepository.watchBarcodes()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())
    }
}