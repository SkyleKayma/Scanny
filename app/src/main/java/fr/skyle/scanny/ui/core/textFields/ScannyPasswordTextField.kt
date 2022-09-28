package fr.skyle.scanny.ui.core.textFields

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import fr.skyle.scanny.R
import kotlinx.coroutines.CoroutineScope


@Composable
fun ScannyPasswordTextField(
    label: String,
    bringIntoViewRequester: BringIntoViewRequester,
    scope: CoroutineScope,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
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
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    ScannyBasicTextField(
        label = label,
        keyboardType = KeyboardType.Password,
        bringIntoViewRequester = bringIntoViewRequester,
        scope = scope,
        value = value,
        onValueChange = onValueChange,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
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
        trailingIcon = {
            val iconId = if (passwordVisible) R.drawable.ic_visibility else R.drawable.ic_visibility_off

            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = { passwordVisible = !passwordVisible }) {
                Icon(painterResource(id = iconId), "")
            }
        },
        onDone = onDone
    )
}