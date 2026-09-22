package com.thestackdigest.app.data.remote.rss

data class RssItemDto(
    val guid: String,
    val title: String,
    val link: String,
    val creator: String?,
    val pubDate: String,
    val featuredImage: String?,
    val description: String?,
    val contentHtml: String?,
    val categories: List<String>
)