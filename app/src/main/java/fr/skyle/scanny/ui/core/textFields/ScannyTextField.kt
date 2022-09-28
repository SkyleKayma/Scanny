package fr.skyle.scanny.ui.core.textFields

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import fr.skyle.scanny.R
import kotlinx.coroutines.CoroutineScope


@Composable
fun ScannyTextField(
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
    leadingIconId: Int? = null,
    onDone: (() -> Unit)? = null
) {
    ScannyBasicTextField(
        label = label,
        keyboardType = keyboardType,
        bringIntoViewRequester = bringIntoViewRequester,
        scope = scope,
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
        leadingIcon = if (leadingIconId != null) {
            {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = leadingIconId),
                    contentDescription = ""
                )
            }
        } else null,
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