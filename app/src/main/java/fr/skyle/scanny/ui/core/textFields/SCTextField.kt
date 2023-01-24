package fr.skyle.scanny.ui.core.textFields

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import fr.skyle.scanny.R


@Composable
fun ScannyCleanableTextField(
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
    onDone: (() -> Unit)? = null
) {
    ScannyTextField(
        label = label,
        keyboardType = keyboardType,
        bringIntoViewRequester = bringIntoViewRequester,
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        visualTransformation = visualTransformation,
        isError = isError,
        errorText = errorText,
        capitalization = capitalization,
        autoCorrect = autoCorrect,
        imeAction = imeAction,
        modifier = modifier,
        maxLines = maxLines,
        leadingIcon = leadingIcon,
        trailingIcon = if (value.text.isNotEmpty()) {
            {
                IconButton(onClick = {
                    onValueChange(TextFieldValue(""))
                }) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = ""
                    )
                }
            }
        } else null,
        onDone = onDone
    )
}