package fr.skyle.scanny.ui.core.textFields

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import fr.skyle.scanny.ext.clearFocusOnKeyboardDismiss
import fr.skyle.scanny.theme.SCAppTheme
import kotlinx.coroutines.launch

@Composable
fun ScannyTextField(
    label: String,
    bringIntoViewRequester: BringIntoViewRequester,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    keyboardType: KeyboardType,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorText: String? = null,
    imeAction: ImeAction = ImeAction.Default,
    maxLines: Int = Int.MAX_VALUE,
    visualTransformation: VisualTransformation? = null,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    autoCorrect: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onDone: (() -> Unit)? = null
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    // Remember
    val scope = rememberCoroutineScope()
    var hasFocus by remember { mutableStateOf(false) }
    val isErrorState by remember(isError) { mutableStateOf(isError) }

    Column {
        OutlinedTextField(
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
                focusedBorderColor = SCAppTheme.colors.primary,
                unfocusedBorderColor = SCAppTheme.colors.backgroundBlack,
                textColor = SCAppTheme.colors.primary,
                errorBorderColor = SCAppTheme.colors.error,
                unfocusedLabelColor = SCAppTheme.colors.textDark,
                focusedLabelColor = SCAppTheme.colors.primary,
                errorLabelColor = SCAppTheme.colors.error,
                trailingIconColor = SCAppTheme.colors.primary,
                backgroundColor = SCAppTheme.colors.backgroundLight
            ),
            shape = RoundedCornerShape(12.dp),
            label = {
                Text(
                    text = label,
                    style = if (hasFocus || value.text.isNotBlank()) {
                        SCAppTheme.typography.subtitle2
                    } else SCAppTheme.typography.body2
                )
            },
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            visualTransformation = visualTransformation ?: VisualTransformation.None,
            textStyle = SCAppTheme.typography.body1,
            maxLines = maxLines,
            singleLine = maxLines == 1,
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
                color = SCAppTheme.colors.error,
                style = SCAppTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}