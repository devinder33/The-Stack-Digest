package com.thestackdigest.app.ui.utils


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