package com.leoapps.eggy.base.presentation.utils

private const val MILLIS_IN_SECOND = 1000
private const val SECONDS_IN_MINUTE = 60
private const val MINUTES_IN_HOUR = 60
private const val TIME_FORMAT = "%02d:%02d"

fun convertMsToText(ms: Long): String {
    val totalSeconds = ms / MILLIS_IN_SECOND
    val minutes = (totalSeconds / SECONDS_IN_MINUTE) % MINUTES_IN_HOUR
    val seconds = totalSeconds % SECONDS_IN_MINUTE
    return String.format(TIME_FORMAT, minutes, seconds)
}