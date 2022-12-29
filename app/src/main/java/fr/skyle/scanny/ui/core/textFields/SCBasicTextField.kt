package fr.skyle.scanny.ui.core.textFields

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import fr.skyle.scanny.ext.clearFocusOnKeyboardDismiss
import fr.skyle.scanny.theme.SCAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ScannyBasicTextField(
    label: String,
    keyboardType: KeyboardType,
    bringIntoViewRequester: BringIntoViewRequester,
    scope: CoroutineScope,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    visualTransformation: VisualTransformation? = null,
    isError: Boolean = false,
    errorText: String? = null,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    autoCorrect: Boolean = true,
    imeAction: ImeAction = ImeAction.Default,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onDone: (() -> Unit)? = null
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var hasFocus by remember { mutableStateOf(false) }

    val isErrorState by remember(isError) { mutableStateOf(isError) }

    Column {
        OutlinedTextField(
            textStyle = SCAppTheme.typography.body1,
            modifier = modifier
                .fillMaxWidth()
                .clearFocusOnKeyboardDismiss()
                .onFocusEvent {
                    hasFocus = it.hasFocus
                    if (it.hasFocus) {
                        scope.launch {
                            bringIntoViewRequester.bringIntoView()
                        }
                    }
                },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.primary,
                unfocusedBorderColor = MaterialTheme.colors.onSurface,
                textColor = MaterialTheme.colors.primary,
                errorBorderColor = MaterialTheme.colors.error,
                unfocusedLabelColor = MaterialTheme.colors.onSurface,
                focusedLabelColor = MaterialTheme.colors.primary,
                errorLabelColor = MaterialTheme.colors.error,
                trailingIconColor = MaterialTheme.colors.primary
            ),
            shape = RoundedCornerShape(12.dp),
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            visualTransformation = visualTransformation ?: VisualTransformation.None,
            maxLines = maxLines,
            singleLine = maxLines == 1,
            label = {
                Text(
                    text = label,
                    style = if (hasFocus || value.text.isNotBlank()) {
                        MaterialTheme.typography.subtitle2
                    } else MaterialTheme.typography.body2
                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = imeAction,
                keyboardType = keyboardType,
                capitalization = capitalization,
                autoCorrect = autoCorrect
            ),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
                onDone?.invoke()
            }),
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon
        )

        if (isErrorState && !errorText.isNullOrBlank()) {
            Text(
                text = errorText,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}