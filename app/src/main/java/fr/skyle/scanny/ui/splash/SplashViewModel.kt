package fr.skyle.scanny.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    val splashTimer: StateFlow<Boolean> by lazy {
        flow {
            delay(1L)
            emit(true)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), false)
    }
}