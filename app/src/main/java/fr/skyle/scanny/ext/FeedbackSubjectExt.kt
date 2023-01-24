package fr.skyle.scanny.ext

import android.content.Context
import androidx.annotation.StringRes
import fr.skyle.scanny.R
import fr.skyle.scanny.enums.FeedbackSubject

@get:StringRes
val FeedbackSubject.tag: Int
    get() = when (this) {
        FeedbackSubject.SCAN_ISSUE ->
            R.string.feedback_subject_scan_issue_tag

        FeedbackSubject.QR_CODE_DETECTION_ISSUE ->
            R.string.feedback_subject_qrcode_type_detection_tag

        FeedbackSubject.OTHER_ISSUE ->
            R.string.feedback_subject_other_tag
    }

@get:StringRes
val FeedbackSubject.text: Int
    get() = when (this) {
        FeedbackSubject.SCAN_ISSUE ->
            R.string.feedback_subject_scan_issue

        FeedbackSubject.QR_CODE_DETECTION_ISSUE ->
            R.string.feedback_subject_qrcode_type_detection

        FeedbackSubject.OTHER_ISSUE ->
            R.string.feedback_subject_other
    }

fun FeedbackSubject.Companion.fromText(context: Context, text: String): FeedbackSubject =
    FeedbackSubject.values().find { context.getString(it.text) == text } ?: FeedbackSubject.OTHER_ISSUE