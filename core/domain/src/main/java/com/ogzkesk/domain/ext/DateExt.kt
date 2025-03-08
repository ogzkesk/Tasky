package com.ogzkesk.domain.ext

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

private const val DatePattern = "MM-dd-yyyy"

fun Long.toLocalDateTime(): LocalDateTime {
    return Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault()).toLocalDateTime()
}

fun LocalDateTime.toEpochSecond(): Long {
    return this.atZone(ZoneId.systemDefault()).toEpochSecond()
}

fun Long.format(pattern: String = DatePattern): String {
    val date = Date(this)
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    return formatter.format(date)
}

fun LocalDateTime.format(pattern: String = DatePattern): String {
    return format(DateTimeFormatter.ofPattern(pattern))
}

fun Date.format(pattern: String = DatePattern): String {
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    return formatter.format(this)
}
