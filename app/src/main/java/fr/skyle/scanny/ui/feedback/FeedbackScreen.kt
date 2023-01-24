package fr.skyle.scanny.ui.feedback

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import fr.skyle.scanny.enums.FeedbackSubject
import fr.skyle.scanny.theme.SCAppTheme
import fr.skyle.scanny.theme.SCTheme
import fr.skyle.scanny.ui.core.SystemIconsColor
import fr.skyle.scanny.ui.feedback.components.FeedbackScreenContent

@Composable
fun FeedbackScreen(
    navigateBack: () -> Unit,
    onSendFeedback: (FeedbackSubject, String) -> Unit
) {
    // Set system icons color
    SystemIconsColor(
        statusBarDarkIcons = true,
        navigationBarDarkIcons = true,
        navigationBarColor = SCAppTheme.colors.transparent
    )

    FeedbackScreenContent(
        navigateBack = navigateBack,
        onSendFeedback = onSendFeedback
    )
}

@Preview
@Composable
fun PreviewFeedbackScreen() {
    SCTheme {
        FeedbackScreen(
            navigateBack = {},
            onSendFeedback = { _, _ -> }
        )
    }
}