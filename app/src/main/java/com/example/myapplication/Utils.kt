package com.example.myapplication

import java.text.SimpleDateFormat
import java.util.*

fun Long.toFormattedTime(): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    return sdf.format(Date(this))
}
