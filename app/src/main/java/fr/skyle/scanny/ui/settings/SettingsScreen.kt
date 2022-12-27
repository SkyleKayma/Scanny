package fr.skyle.scanny.ui.settings

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.skyle.scanny.BuildConfig
import fr.skyle.scanny.R
import fr.skyle.scanny.theme.SCAppTheme
import fr.skyle.scanny.theme.SCTheme
import fr.skyle.scanny.ui.core.TitleText

@Composable
fun SettingsScreen(
    goToNotifications: () -> Unit,
    goToFeedback: () -> Unit,
    goToDataPrivacy: () -> Unit,
    goToRateTheApp: () -> Unit,
    goToAbout: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        contentPadding = PaddingValues(24.dp)
    ) {
        item {
            TitleText(
                textId = R.string.settings_title_general
            )
        }
        item {
            Spacer(
                modifier = Modifier.height(16.dp)
            )
        }
        item {
            SettingsCell(
                startIconId = R.drawable.ic_notifications,
                textId = R.string.settings_notifications,
                textColor = SCAppTheme.colors.textPrimary,
                onClick = goToNotifications
            )
        }
        item {
            Spacer(
                modifier = Modifier.height(16.dp)
            )
        }
        item {
            SettingsCell(
                startIconId = R.drawable.ic_feedback,
                textId = R.string.settings_feedback,
                textColor = SCAppTheme.colors.textPrimary,
                onClick = goToFeedback
            )
        }
        item {
            Spacer(
                modifier = Modifier.height(16.dp)
            )
        }
        item {
            SettingsCell(
                startIconId = R.drawable.ic_rate_app,
                textId = R.string.settings_rate_app,
                textColor = SCAppTheme.colors.textPrimary,
                onClick = goToRateTheApp
            )
        }
        item {
            Spacer(
                modifier = Modifier.height(24.dp)
            )
        }
        item {
            TitleText(
                textId = R.string.settings_title_legal
            )
        }
        item {
            Spacer(
                modifier = Modifier.height(16.dp)
            )
        }
        item {
            SettingsCell(
                startIconId = R.drawable.ic_data_privacy,
                textId = R.string.settings_data_privacy,
                textColor = SCAppTheme.colors.textPrimary,
                onClick = goToDataPrivacy
            )
        }
        item {
            Spacer(
                modifier = Modifier.height(16.dp)
            )
        }
        item {
            SettingsCell(
                startIconId = R.drawable.ic_about,
                textId = R.string.settings_about,
                textColor = SCAppTheme.colors.textPrimary,
                onClick = goToAbout
            )
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
                text = stringResource(id = R.string.settings_version, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE.toString()),
                color = SCAppTheme.colors.textPrimary,
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Composable
fun SettingsCell(
    @DrawableRes startIconId: Int,
    @StringRes textId: Int,
    textColor: Color,
    onClick: () -> Unit,
    @DrawableRes endIconId: Int? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .background(colorResource(id = R.color.sc_background_primary))
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(100))
                .background(SCAppTheme.colors.backgroundLight)
                .padding(12.dp)
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = startIconId),
                contentDescription = stringResource(id = textId),
                tint = SCAppTheme.colors.primary
            )
        }

        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp),
            text = stringResource(id = textId),
            style = MaterialTheme.typography.body1,
            color = textColor,
            textAlign = TextAlign.Start
        )

        endIconId?.let {
            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = it),
                contentDescription = "",
                tint = SCAppTheme.colors.primary
            )
        }
    }
}

@Preview
@Composable
fun PreviewSettingsScreen() {
    SCTheme {
        SettingsScreen(
            goToNotifications = {},
            goToFeedback = {},
            goToRateTheApp = {},
            goToDataPrivacy = {},
            goToAbout = {}
        )
    }
}

@Preview
@Composable
fun PreviewSettingsCell() {
    SCTheme {
        SettingsCell(
            startIconId = R.drawable.ic_notifications,
            textId = R.string.settings_notifications,
            textColor = SCAppTheme.colors.textPrimary,
            onClick = {}
        )
    }
}