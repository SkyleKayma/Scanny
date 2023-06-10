package fr.skyle.scanny.ext

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalFocusManager

@Suppress("ModifierFactoryUnreferencedReceiver")
fun Modifier.clearFocusOnKeyboardDismiss(): Modifier =
    composed {
        var isFocused by remember { mutableStateOf(false) }
        var keyboardAppearedSinceLastFocused by remember { mutableStateOf(false) }
        if (isFocused) {
            val imeIsVisible = WindowInsets.isImeVisible
            val focusManager = LocalFocusManager.current
            LaunchedEffect(imeIsVisible) {
                if (imeIsVisible) {
                    keyboardAppearedSinceLastFocused = true
                } else if (keyboardAppearedSinceLastFocused) {
                    focusManager.clearFocus()
                }
            }
        }
        onFocusEvent {
            if (isFocused != it.isFocused) {
                isFocused = it.isFocused
                if (isFocused) {
                    keyboardAppearedSinceLastFocused = false
                }
            }
        }
    }

inline fun Modifier.debounceClickable(
    enabled: Boolean = true,
    debounceInterval: Long = 800,
    crossinline onClick: () -> Unit,
): Modifier = composed {
    var lastClickTime by remember { mutableStateOf(0L) }
    clickable(enabled = enabled) {
        val currentTime = System.currentTimeMillis()
        if ((currentTime - lastClickTime) < debounceInterval) return@clickable
        lastClickTime = currentTime
        onClick()
    }
}