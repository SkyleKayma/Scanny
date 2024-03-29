package fr.skyle.scanny.ui.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.skyle.scanny.BuildConfig
import fr.skyle.scanny.R
import fr.skyle.scanny.ext.navigateToDataPrivacy
import fr.skyle.scanny.ext.navigateToOpenium
import fr.skyle.scanny.ext.navigateToRateApp
import fr.skyle.scanny.theme.SCAppTheme
import fr.skyle.scanny.theme.SCTheme
import fr.skyle.scanny.ui.core.SCTopAppBar
import fr.skyle.scanny.ui.core.SettingsTitleText

@Composable
fun SettingsScreenContent(
    navigateToFeedback: () -> Unit,
    navigateToAbout: () -> Unit,
    navigateBack: () -> Unit,
    onVibrationAfterScanChanged: (Boolean) -> Unit,
    onOpenLinkAfterScanChanged: (Boolean) -> Unit,
    onRawContentShownChanged: (Boolean) -> Unit,
    isVibrateAfterScanEnabled: () -> Boolean,
    isOpenLinkAfterScanEnabled: () -> Boolean,
    isRawContentShown: () -> Boolean
) {
    // Context
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier
            .background(SCAppTheme.colors.background)
            .systemBarsPadding(),
        scaffoldState = rememberScaffoldState(),
        topBar = {
            SCTopAppBar(
                modifier = Modifier.background(SCAppTheme.colors.background),
                title = stringResource(id = R.string.settings_title),
                onClickHomeButton = navigateBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(SCAppTheme.colors.background)
                .verticalScroll(rememberScrollState())
                .padding(start = 12.dp, end = 12.dp, top = 12.dp, bottom = 24.dp)
        ) {
            SettingsTitleText(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.settings_general)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(SCAppTheme.colors.backgroundLight)
            ) {
                SettingsSwitchCell(
                    startIconId = R.drawable.ic_vibration,
                    text = stringResource(id = R.string.settings_vibration),
                    textColor = SCAppTheme.colors.textDark,
                    isChecked = isVibrateAfterScanEnabled,
                    onSwitchChecked = onVibrationAfterScanChanged
                )

                SettingsSwitchCell(
                    startIconId = R.drawable.ic_open_link,
                    text = stringResource(id = R.string.settings_open_url),
                    textColor = SCAppTheme.colors.textDark,
                    isChecked = isOpenLinkAfterScanEnabled,
                    onSwitchChecked = onOpenLinkAfterScanChanged
                )

                SettingsSwitchCell(
                    startIconId = R.drawable.ic_code,
                    text = stringResource(id = R.string.settings_show_raw_content),
                    textColor = SCAppTheme.colors.textDark,
                    isChecked = isRawContentShown,
                    onSwitchChecked = onRawContentShownChanged,
                    withDivider = false
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            SettingsTitleText(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.settings_app)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(SCAppTheme.colors.backgroundLight)
            ) {
                SettingsCell(
                    startIconId = R.drawable.ic_about,
                    text = stringResource(id = R.string.settings_about),
                    textColor = SCAppTheme.colors.textDark,
                    onClick = navigateToAbout
                )

                SettingsCell(
                    startIconId = R.drawable.ic_openium_logo,
                    text = stringResource(id = R.string.settings_openium),
                    textColor = SCAppTheme.colors.textDark,
                    onClick = { context.navigateToOpenium() }
                )

                SettingsCell(
                    startIconId = R.drawable.ic_feedback,
                    text = stringResource(id = R.string.settings_feedback),
                    textColor = SCAppTheme.colors.textDark,
                    onClick = navigateToFeedback
                )

                SettingsCell(
                    startIconId = R.drawable.ic_rate_app,
                    text = stringResource(id = R.string.settings_rate_app),
                    textColor = SCAppTheme.colors.textDark,
                    onClick = { context.navigateToRateApp() }
                )

                SettingsCell(
                    startIconId = R.drawable.ic_data_privacy,
                    text = stringResource(id = R.string.settings_data_privacy),
                    textColor = SCAppTheme.colors.textDark,
                    onClick = { context.navigateToDataPrivacy() },
                    withDivider = false
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
                text = stringResource(id = R.string.settings_version, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE.toString()),
                color = SCAppTheme.colors.text,
                style = SCAppTheme.typography.caption
            )
        }
    }
}

@Preview
@Composable
fun PreviewSettingsScreenContent() {
    SCTheme {
        SettingsScreenContent(
            navigateToFeedback = {},
            navigateToAbout = {},
            navigateBack = {},
            onVibrationAfterScanChanged = {},
            onOpenLinkAfterScanChanged = {},
            onRawContentShownChanged = {},
            isVibrateAfterScanEnabled = { false },
            isOpenLinkAfterScanEnabled = { false },
            isRawContentShown = { false }
        )
    }
}