package com.thestackdigest.app.ui.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter


fun formatRelativeTime(
    timestamp: Long,
    currentTime: Long = System.currentTimeMillis()
): String {

    val difference = currentTime - timestamp

    val minutes = difference / (60 * 1000)
    val hours = difference / (60 * 60 * 1000)
    val days = difference / (24 * 60 * 60 * 1000)

    return when {
        minutes < 1 -> "Just now"
        minutes < 60 -> "$minutes min ago"
        hours < 24 -> "$hours hr ago"
        days == 1L -> "Yesterday"
        days < 7 -> "$days days ago"
        else -> "$days days ago"
    }
}

fun formatPublishedDate(
    timestamp: Long
): String {

    if (timestamp <= 0L) {
        return ""
    }

    return Instant
        .ofEpochMilli(timestamp)
        .atZone(ZoneId.systemDefault())
        .format(
            DateTimeFormatter.ofPattern(
                "MMM d, yyyy"
            )
        )
}

fun estimateReadTime(
    text: String
): Int {

    if (text.isBlank()) {
        return 1
    }

    val wordCount = text
        .trim()
        .split(Regex("\\s+"))
        .size

    val wordsPerMinute = 200

    return maxOf(
        1,
        (wordCount + wordsPerMinute - 1) /
                wordsPerMinute
    )
}