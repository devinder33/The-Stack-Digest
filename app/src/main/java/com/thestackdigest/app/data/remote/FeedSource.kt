package com.thestackdigest.app.data.remote


enum class FeedFormat {
    RSS,
    ATOM
}

data class FeedSource(
    val name: String,
    val url: String,
    val format: FeedFormat
)

val feedSources = listOf(
    FeedSource(
        name = "Android Developers",
        url = "https://android-developers.googleblog.com/atom.xml",
        format = FeedFormat.ATOM
    ),
    FeedSource(
        name = "Kotlin Blog",
        url = "https://blog.jetbrains.com/kotlin/feed/",
        format = FeedFormat.RSS
    )
)