package fr.skyle.scanny.ui.core.switchs

import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.runtime.Composable
import fr.skyle.scanny.theme.SCAppTheme

@Composable
fun SCSwitch(
    isSwitchChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Switch(
        colors = SwitchDefaults.colors(
            checkedThumbColor = SCAppTheme.colors.primary,
            checkedTrackColor = SCAppTheme.colors.primary.copy(alpha = 0.3f),
            uncheckedThumbColor = SCAppTheme.colors.nuance40,
            uncheckedTrackColor = SCAppTheme.colors.nuance40.copy(alpha = 0.3f)
        ),
        checked = isSwitchChecked,
        onCheckedChange = {
            onCheckedChange(it)
        }
    )
}