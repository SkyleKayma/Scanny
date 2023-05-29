package fr.skyle.scanny.ui.feedback

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import fr.skyle.scanny.theme.SCAppTheme
import fr.skyle.scanny.theme.SCTheme
import fr.skyle.scanny.ui.core.SystemIconsColor
import fr.skyle.scanny.ui.feedback.components.FeedbackScreenContent

@Composable
fun FeedbackScreen(
    navigateBack: () -> Unit
) {
    // Set system icons color
    SystemIconsColor(
        statusBarDarkIcons = true,
        navigationBarDarkIcons = true,
        navigationBarColor = SCAppTheme.colors.transparent
    )

    FeedbackScreenContent(
        navigateBack = navigateBack
    )
}

@Preview
@Composable
fun PreviewFeedbackScreen() {
    SCTheme {
        FeedbackScreen(
            navigateBack = {}
        )
    }
}