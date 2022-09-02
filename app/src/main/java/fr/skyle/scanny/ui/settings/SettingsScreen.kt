package fr.skyle.scanny.ui.settings

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.skyle.scanny.BuildConfig
import fr.skyle.scanny.R
import fr.skyle.scanny.theme.ScannyTheme
import fr.skyle.scanny.ui.core.TitleText

private val settingsGeneralSections =
    listOf(
        SettingsItem(R.drawable.ic_notifications, R.string.settings_notifications, R.color.sc_title),
        SettingsItem(R.drawable.ic_feedback, R.string.settings_feedback, R.color.sc_title),
        SettingsItem(R.drawable.ic_rate_app, R.string.settings_rate_app, R.color.sc_title)
    )

private val settingsLegalSections =
    listOf(
        SettingsItem(R.drawable.ic_cgu, R.string.settings_cgu, R.color.sc_title),
        SettingsItem(R.drawable.ic_data_privacy, R.string.settings_data_privacy, R.color.sc_title),
        SettingsItem(R.drawable.ic_about, R.string.settings_about, R.color.sc_title)
    )

@Composable
fun SettingsScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(24.dp)
    ) {
        item {
            TitleText(textId = R.string.settings_title_general)
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            SettingsSection(settingsItems = settingsGeneralSections)
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            TitleText(textId = R.string.settings_title_legal)
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            SettingsSection(settingsItems = settingsLegalSections)
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
                text = stringResource(id = R.string.settings_version, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE.toString()),
                color = colorResource(id = R.color.sc_title),
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Composable
fun SettingsSection(settingsItems: List<SettingsItem>) {
    Column {
        settingsItems.forEachIndexed { _, item ->
            SettingsCell(settingItem = item)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun SettingsCell(settingItem: SettingsItem) {
    val context = LocalContext.current
    val contentColor = colorResource(settingItem.textColorId)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(10.dp))
            .background(colorResource(id = R.color.sc_background_secondary))
            .padding(horizontal = 20.dp, vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = settingItem.startIconId),
            contentDescription = stringResource(id = settingItem.textId),
            colorFilter = ColorFilter.tint(colorResource(id = R.color.sc_icon_secondary))
        )
        Text(
            text = context.getString(settingItem.textId),
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp),
            style = MaterialTheme.typography.body1,
            color = contentColor,
            textAlign = TextAlign.Start
        )
        settingItem.endIconId?.let {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = it),
                contentDescription = "",
                colorFilter = ColorFilter.tint(colorResource(id = R.color.sc_icon_primary))
            )
        }
    }
}

@Preview
@Composable
fun PreviewSettingsScreen() {
    ScannyTheme {
        SettingsScreen()
    }
}

@Preview
@Composable
fun PreviewSettingsSection() {
    ScannyTheme {
        SettingsSection(settingsGeneralSections)
    }
}

@Preview
@Composable
fun PreviewSettingsCellFirstAndLast() {
    ScannyTheme {
        SettingsCell(SettingsItem(R.drawable.ic_scan_qr, R.string.home_scan, R.color.sc_title))
    }
}

@Preview
@Composable
fun PreviewSettingsCellFirst() {
    ScannyTheme {
        SettingsCell(SettingsItem(R.drawable.ic_scan_qr, R.string.home_scan, R.color.sc_title))
    }
}

@Preview
@Composable
fun PreviewSettingsCellLast() {
    ScannyTheme {
        SettingsCell(SettingsItem(R.drawable.ic_scan_qr, R.string.home_scan, R.color.sc_title))
    }
}

data class SettingsItem(
    @DrawableRes val startIconId: Int,
    @StringRes val textId: Int,
    @ColorRes val textColorId: Int,
    @DrawableRes val endIconId: Int? = R.drawable.ic_arrow_right
)