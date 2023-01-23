package fr.skyle.scanny.ui.about.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.skyle.scanny.BuildConfig
import fr.skyle.scanny.R
import fr.skyle.scanny.theme.SCAppTheme
import fr.skyle.scanny.theme.SCTheme
import fr.skyle.scanny.ui.core.SCTopAppBar

@Composable
fun AboutScreenContent(
    navigateBack: () -> Unit,
    onOpenLink: (String) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .background(SCAppTheme.colors.background)
            .systemBarsPadding(),
        scaffoldState = rememberScaffoldState(),
        topBar = {
            SCTopAppBar(
                modifier = Modifier.background(SCAppTheme.colors.background),
                title = stringResource(id = R.string.about_title),
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
                .padding(24.dp)
        ) {
            Image(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterHorizontally),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = ""
            )

            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(SCAppTheme.colors.backgroundLight)
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.width(IntrinsicSize.Max)
                ) {
                    Text(
                        modifier = Modifier.wrapContentSize(),
                        text = stringResource(id = R.string.app_name),
                        style = SCAppTheme.typography.h1,
                        color = SCAppTheme.colors.textDark,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp),
                        color = SCAppTheme.colors.backgroundBlack
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.about_version),
                    style = SCAppTheme.typography.body3,
                    color = SCAppTheme.colors.primary,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.settings_version, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE.toString()),
                    style = SCAppTheme.typography.body1,
                    color = SCAppTheme.colors.textDark,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.about_created_by),
                    style = SCAppTheme.typography.body3,
                    color = SCAppTheme.colors.primary,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.about_created_by_desc),
                    style = SCAppTheme.typography.body1,
                    color = SCAppTheme.colors.textDark,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.about_github),
                    style = SCAppTheme.typography.body3,
                    color = SCAppTheme.colors.primary,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )

                val githubLink = stringResource(id = R.string.about_github_link)
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onOpenLink(githubLink)
                        },
                    text = githubLink,
                    style = SCAppTheme.typography.body1,
                    color = SCAppTheme.colors.textDark,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.about_desc),
                    style = SCAppTheme.typography.body1,
                    color = SCAppTheme.colors.textDark
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    text = stringResource(id = R.string.about_thanks_message),
                    style = SCAppTheme.typography.h2,
                    color = SCAppTheme.colors.textDark,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewAboutScreenContent() {
    SCTheme {
        AboutScreenContent(
            navigateBack = {},
            onOpenLink = {}
        )
    }
}