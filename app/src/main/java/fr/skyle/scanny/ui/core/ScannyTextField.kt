package fr.skyle.scanny.ui.core

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ScannyTextField(
    label: String,
    keyboardType: KeyboardType,
    bringIntoViewRequester: BringIntoViewRequester,
    scope: CoroutineScope,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    trailingIconId: Int? = null,
    onDone: (() -> Unit)? = null
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val hasFocus by remember { mutableStateOf(false) }
    var valid by remember { mutableStateOf(false) }

    valid = value.text.isNotBlank()

    OutlinedTextField(
        textStyle = MaterialTheme.typography.body1,
        modifier = modifier
            .fillMaxWidth()
            .onFocusEvent {
                if (it.hasFocus) {
                    scope.launch {
                        bringIntoViewRequester.bringIntoView()
                    }
                }
            },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colors.primary,
            unfocusedBorderColor = if (valid) {
                MaterialTheme.colors.primaryVariant
            } else MaterialTheme.colors.onSurface,
            textColor = MaterialTheme.colors.primary,
            errorBorderColor = MaterialTheme.colors.error,
            unfocusedLabelColor = if (valid) {
                MaterialTheme.colors.primaryVariant
            } else MaterialTheme.colors.onSurface,
            focusedLabelColor = MaterialTheme.colors.primary,
            errorLabelColor = MaterialTheme.colors.error,
            trailingIconColor = MaterialTheme.colors.primary
        ),
        shape = RoundedCornerShape(12.dp),
        value = value,
        onValueChange = {
            onValueChange(it)
        },
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
        keyboardOptions = KeyboardOptions(imeAction = if (maxLines == 1) ImeAction.Done else ImeAction.Default, keyboardType = keyboardType),
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hide()
            onDone?.invoke()
        }),

        trailingIcon = {
            if (value.text.isNotEmpty() && trailingIconId != null) {
                IconButton(onClick = {
                    onValueChange(TextFieldValue(""))
                }) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = trailingIconId),
                        contentDescription = ""
                    )
                }
            }
        }
    )
}