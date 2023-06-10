package fr.skyle.scanny.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.skyle.scanny.data.repository.BarcodeDataRepository
import fr.skyle.scanny.data.vo.BarcodeData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HistoryScreenViewModel @Inject constructor(
    barcodeDataRepository: BarcodeDataRepository
) : ViewModel() {

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Loading)
    val state: StateFlow<State> by lazy { _state.asStateFlow() }

    init {
        barcodeDataRepository.watchBarcodes()
            .onEach {
                if (it.isEmpty()) {
                    _state.emit(State.Empty)
                } else _state.emit(State.Loaded(it))
            }.launchIn(viewModelScope)
    }

    sealed interface State {
        object Loading : State
        data class Loaded(val barcodes: List<BarcodeData>) : State
        object Empty : State
    }
}