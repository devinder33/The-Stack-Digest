package com.thestackdigest.app.data.remote.rss

import com.thestackdigest.app.domain.model.Article
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun RssItemDto.toArticle(
    sourceName: String
): Article {

    return Article(
        id = guid.ifBlank { link },
        title = title,
        description = description.orEmpty(),
        articleUrl = link,
        imageUrl = featuredImage,
        sourceName = sourceName,
        authorName = creator,
        publishedAt = parseRssDate(pubDate),
        updatedAt = null,
        categories = categories,
        isSaved = false
    )
}

private fun parseRssDate(date: String): Long {
    return try {
        ZonedDateTime
            .parse(date, DateTimeFormatter.RFC_1123_DATE_TIME)
            .toInstant()
            .toEpochMilli()
    } catch (e: Exception) {
        0L
    }
}