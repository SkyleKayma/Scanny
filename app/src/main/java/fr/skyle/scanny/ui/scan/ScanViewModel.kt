package fr.skyle.scanny.ui.scan

import androidx.camera.core.ImageAnalysis
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.skyle.scanny.utils.BarCodeAnalyzer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import javax.inject.Inject

@HiltViewModel
class ScanViewModel @Inject constructor() : ViewModel() {

    private val cameraExecutor = Executors.newSingleThreadExecutor()
    private var barCodeAnalyzer: BarCodeAnalyzer? = null
    private var imageAnalysis: ImageAnalysis? = null

    val scanEvent: StateFlow<State>
        get() = _scanEvent.asStateFlow()
    private val _scanEvent = MutableStateFlow<State>(State.NONE)

    fun getImageAnalysis(): ImageAnalysis {
        if (imageAnalysis == null) {
            imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build().apply {
                    barCodeAnalyzer = BarCodeAnalyzer {
                        // Send event
                        _scanEvent.tryEmit(State.SCANNING)

                        val stringValueRead = it.firstOrNull()?.displayValue

                        // Check content
                        if (stringValueRead?.isNotBlank() == true) {

                            // Disable scanning when we are showing results
                            barCodeAnalyzer?.canDetectCode = false

                            viewModelScope.launch {
                                // Send event
                                _scanEvent.tryEmit(State.SUCCESS(stringValueRead))
                            }
                        }
                    }
                    barCodeAnalyzer?.let {
                        setAnalyzer(cameraExecutor, it)
                    }
                }
        }
        return imageAnalysis!!
    }

    fun reactivateQRCodeScan() {
        barCodeAnalyzer?.canDetectCode = true
    }

    sealed class State {
        object SCANNING : State()
        data class SUCCESS(val stringValue: String) : State()
        object NONE : State()
    }
}