package fr.skyle.scanny.ext

import android.content.Context
import fr.skyle.scanny.R
import fr.skyle.scanny.enums.DateFormat
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone


val DateFormat.stringId: Int
    get() = when (this) {
        DateFormat.dd_MM_yyyy_HHmm ->
            R.string.date_format_dd_MM_yyyy_HHmm
    }

fun Long.format(context: Context, dateFormat: DateFormat, timeZone: String? = null): String? =
    try {
        dateFormat.getFormatterFromFormat(context, timeZone).format(this)
    } catch (e: Exception) {
        Timber.e(e)
        null
    }

fun DateFormat.getFormatterFromFormat(context: Context, timeZone: String?): SimpleDateFormat =
    SimpleDateFormat(context.getString(stringId), Locale.getDefault()).apply {
        timeZone?.let {
            this.timeZone = TimeZone.getTimeZone(it)
        }
    }

fun String.parse(context: Context, dateFormat: DateFormat): Date? =
    try {
        dateFormat.getFormatterFromFormat(context, null).parse(this)
    } catch (e: Exception) {
        Timber.e(e)
        null
    }