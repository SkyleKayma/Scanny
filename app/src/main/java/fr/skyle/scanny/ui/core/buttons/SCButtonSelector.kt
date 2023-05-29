package fr.skyle.scanny.ui.core.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.skyle.scanny.theme.SCAppTheme

@Composable
fun SCButtonSelector(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        onClick = onClick,
        border = BorderStroke(0.dp, MaterialTheme.colors.primaryVariant)
    ) {
        Text(
            text = text,
            color = SCAppTheme.colors.textPrimary
        )
    }
}