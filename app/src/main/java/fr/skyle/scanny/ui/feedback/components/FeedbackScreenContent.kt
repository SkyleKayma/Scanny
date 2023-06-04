package fr.skyle.scanny.ui.feedback.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.skyle.scanny.R
import fr.skyle.scanny.enums.FeedbackSubject
import fr.skyle.scanny.ext.fromText
import fr.skyle.scanny.ext.sendFeedback
import fr.skyle.scanny.ext.text
import fr.skyle.scanny.theme.SCAppTheme
import fr.skyle.scanny.theme.SCTheme
import fr.skyle.scanny.ui.core.SCSpinner
import fr.skyle.scanny.ui.core.SCTopAppBarWithHomeButton
import fr.skyle.scanny.ui.core.buttons.SCButton
import fr.skyle.scanny.ui.core.textFields.ScannyCleanableTextField
import kotlinx.coroutines.launch

@Composable
fun FeedbackScreenContent(
    navigateBack: () -> Unit
) {
    // Context
    val context = LocalContext.current

    // Remember
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    var feedbackSubject by remember { mutableStateOf<FeedbackSubject?>(null) }
    var message by remember { mutableStateOf(TextFieldValue("")) }

    val modeEntries by remember {
        mutableStateOf(context.resources.getStringArray(R.array.feedback_subject_list).toList())
    }

    Scaffold(
        modifier = Modifier
            .background(SCAppTheme.colors.nuance90)
            .statusBarsPadding(),
        scaffoldState = scaffoldState,
        topBar = {
            SCTopAppBarWithHomeButton(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = SCAppTheme.colors.nuance90,
                title = stringResource(id = R.string.feedback_title),
                onClickHomeButton = navigateBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(SCAppTheme.colors.nuance90)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            Text(
                text = stringResource(id = R.string.feedback_subject_title),
                style = SCAppTheme.typography.body2,
                color = SCAppTheme.colors.nuance10,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            SCSpinner(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .border(1.dp, SCAppTheme.colors.nuance10, RoundedCornerShape(10.dp))
                    .background(SCAppTheme.colors.nuance100),
                dropDownModifier = Modifier.fillMaxWidth(),
                items = modeEntries,
                selectedItem = stringResource(id = feedbackSubject?.text ?: R.string.feedback_subject_none),
                onItemSelected = {
                    feedbackSubject = FeedbackSubject.fromText(context, it)
                },
                selectedItemFactory = { modifier, item ->
                    Row(
                        modifier = modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = item,
                            style = SCAppTheme.typography.body2,
                            color = SCAppTheme.colors.nuance10,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )

                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = ""
                        )
                    }
                },
                dropdownItemFactory = { item, _ ->
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = item
                    )
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            ScannyCleanableTextField(
                modifier = Modifier.height(200.dp),
                label = stringResource(id = R.string.feedback_message),
                keyboardType = KeyboardType.Text,
                bringIntoViewRequester = bringIntoViewRequester,
                value = message,
                onValueChange = {
                    message = it
                },
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Done
            )

            Spacer(modifier = Modifier.weight(1f))

            SCButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                text = stringResource(id = R.string.feedback_send_mail),
                onClick = {
                    scope.launch {
                        feedbackSubject?.let {
                            context.sendFeedback(it, message.text)
                        } ?: scaffoldState.snackbarHostState.showSnackbar(context.getString(R.string.feedback_error_no_subject))
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewFeedbackScreenContent() {
    SCTheme {
        FeedbackScreenContent(
            navigateBack = {}
        )
    }
}