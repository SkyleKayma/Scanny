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
            checkedThumbColor = SCAppTheme.colors.backgroundPrimary,
            checkedTrackColor = SCAppTheme.colors.backgroundPrimary.copy(alpha = 0.3f),
            uncheckedThumbColor = SCAppTheme.colors.backgroundDisabled,
            uncheckedTrackColor = SCAppTheme.colors.backgroundDisabled.copy(alpha = 0.3f)
        ),
        checked = isSwitchChecked,
        onCheckedChange = {
            onCheckedChange(it)
        }
    )
}