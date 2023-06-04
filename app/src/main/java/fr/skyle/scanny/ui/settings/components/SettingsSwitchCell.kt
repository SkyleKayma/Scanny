package fr.skyle.scanny.ui.settings.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.skyle.scanny.R
import fr.skyle.scanny.theme.SCAppTheme
import fr.skyle.scanny.theme.SCTheme
import fr.skyle.scanny.ui.core.switchs.SCSwitch

@Composable
fun SettingsSwitchCell(
    @DrawableRes startIconId: Int,
    text: String,
    textColor: Color,
    isChecked: () -> Boolean,
    onSwitchChecked: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    withDivider: Boolean = true
) {
    var mIsChecked by remember(isChecked()) {
        mutableStateOf(isChecked())
    }

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clickable {
                    onSwitchChecked(!mIsChecked)
                }
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = startIconId),
                contentDescription = text,
                tint = SCAppTheme.colors.primary
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                modifier = Modifier.weight(1f),
                text = text,
                style = SCAppTheme.typography.body2,
                color = textColor
            )

            SCSwitch(
                isSwitchChecked = mIsChecked,
                onCheckedChange = {
                    mIsChecked = it
                    onSwitchChecked(it)
                }
            )
        }

        if (withDivider) {
            Divider(
                modifier = Modifier.padding(start = 48.dp, end = 16.dp),
                color = SCAppTheme.colors.nuance90
            )
        }
    }
}

@Preview
@Composable
fun PreviewSettingsSwitchCell() {
    SCTheme {
        SettingsSwitchCell(
            startIconId = R.drawable.ic_vibration,
            text = stringResource(id = R.string.settings_vibration),
            textColor = SCAppTheme.colors.nuance10,
            isChecked = { false },
            onSwitchChecked = {}
        )
    }
}