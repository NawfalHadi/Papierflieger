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
