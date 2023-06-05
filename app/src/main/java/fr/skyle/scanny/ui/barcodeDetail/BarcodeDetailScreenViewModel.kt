package fr.skyle.scanny.ui.barcodeDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.skyle.scanny.data.repository.BarcodeDataRepository
import fr.skyle.scanny.data.vo.BarcodeData
import fr.skyle.scanny.navigation.RouteArgument
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BarcodeDetailScreenViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val barcodeDataRepository: BarcodeDataRepository
) : ViewModel() {

    private val barcodeDataId: Long by lazy {
        stateHandle.get<Long>(RouteArgument.ARG_BARCODE_DATA_ID) ?: 0L
    }

    val barcode: StateFlow<BarcodeData?> by lazy {
        barcodeDataRepository.watchBarcode(barcodeDataId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)
    }
}