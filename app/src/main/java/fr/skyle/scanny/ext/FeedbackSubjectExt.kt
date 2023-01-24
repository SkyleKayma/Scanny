package fr.skyle.scanny.ext

import android.content.Context
import androidx.annotation.StringRes
import fr.skyle.scanny.R
import fr.skyle.scanny.enums.FeedbackSubject

@get:StringRes
val FeedbackSubject.tag: Int
    get() = when (this) {
        FeedbackSubject.SCAN ->
            R.string.feedback_tag_scan

        FeedbackSubject.CODE_DETECTION ->
            R.string.feedback_tag_code_detection

        FeedbackSubject.OTHER ->
            R.string.feedback_tag_other
    }

@get:StringRes
val FeedbackSubject.text: Int
    get() = when (this) {
        FeedbackSubject.SCAN ->
            R.string.feedback_subject_scan_issue

        FeedbackSubject.CODE_DETECTION ->
            R.string.feedback_subject_qrcode_type_detection

        FeedbackSubject.OTHER ->
            R.string.feedback_subject_other
    }

fun FeedbackSubject.Companion.fromText(context: Context, text: String): FeedbackSubject =
    FeedbackSubject.values().find { context.getString(it.text) == text } ?: FeedbackSubject.OTHER