package com.winphyoethu.entain.common.util

/**
 * Extension function to convert seconds to remaining time
 *
 */
fun Long.secondsToTime(): String {

    val minutes = this / 60
    val hours = minutes / 60
    val days = hours / 24

    return when {
        this < 0 -> "LIVE"
        days > 0 -> "$days day${if (days > 1) "s" else ""}"
        hours in 1..23 -> {
            if (hours < 2) {
                "${hours}h ${minutes % 60}m"
            } else {
                "${hours}h"
            }
        }
        minutes in 1..59 -> {
            if (minutes < 5) {
                "${minutes}m ${this % 60}s"
            } else {
                "${minutes}m"
            }
        }
        else -> "${this}s"
    }
}