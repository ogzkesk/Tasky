package com.ogzkesk.database.util

import android.text.format.DateUtils
import java.util.Date

fun Long.getRelativeTimeSpanString(): String {
    val date = Date(this)
    return if (Date().time - date.time < 2 * DateUtils.MINUTE_IN_MILLIS) {
        "Just Now"
    } else {
        DateUtils.getRelativeTimeSpanString(
            date.time,
            Date().time,
            DateUtils.MINUTE_IN_MILLIS,
            DateUtils.FORMAT_SHOW_DATE
        ).toString()
    }
}