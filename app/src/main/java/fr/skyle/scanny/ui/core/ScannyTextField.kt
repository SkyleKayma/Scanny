package fr.skyle.scanny.ui.core

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun ScannyTextField(
    initialValue: String,
    label: String,
    keyboardType: KeyboardType,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val hasFocus by remember { mutableStateOf(false) }
    var valid by remember { mutableStateOf(false) }
    var value by remember { mutableStateOf(initialValue) }

    valid = value.isNotBlank()

    OutlinedTextField(
        textStyle = MaterialTheme.typography.body2,
        modifier = modifier
            .fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colors.primaryVariant,
            unfocusedBorderColor = if (valid) {
                MaterialTheme.colors.primaryVariant
            } else {
                MaterialTheme.colors.onSurface
            },
            textColor = MaterialTheme.colors.secondary,
            errorBorderColor = MaterialTheme.colors.error,
            unfocusedLabelColor = if (valid) {
                MaterialTheme.colors.primaryVariant
            } else MaterialTheme.colors.secondaryVariant,
            focusedLabelColor = MaterialTheme.colors.primaryVariant,
            errorLabelColor = MaterialTheme.colors.error
        ),
        shape = RoundedCornerShape(12.dp),
        value = value,
        onValueChange = {
            value = it
            onValueChange(it)
        },
        label = {
            Text(
                text = label,
                style = if (hasFocus || value.isNotBlank()) {
                    MaterialTheme.typography.subtitle2
                } else MaterialTheme.typography.body2
            )
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = keyboardType),
        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
    )
}