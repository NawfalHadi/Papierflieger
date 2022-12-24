package com.papierflieger.wrapper

import java.text.SimpleDateFormat
import java.util.*

fun Long.toDate(): String {
    val format = SimpleDateFormat(
        "EEE, dd MMM yyyy",
        Locale.getDefault()
    )
    return format.format(Date(this))
}

fun Long.toRequestDate(): String {
    val format = SimpleDateFormat(
        "yyyy-MM-dd",
        Locale.getDefault()
    )
    return format.format(Date(this))
}
