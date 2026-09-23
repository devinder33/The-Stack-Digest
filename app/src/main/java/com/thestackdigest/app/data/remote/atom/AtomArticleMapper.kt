package com.thestackdigest.app.data.remote.atom

import android.text.Html
import com.thestackdigest.app.domain.model.Article
import java.time.OffsetDateTime

fun AtomEntryDto.toArticle(
    sourceName: String
): Article {

    return Article(
        id = id.ifBlank { articleUrl },
        title = title,
        description = contentHtml.toPlainText(),
        articleUrl = articleUrl,
        imageUrl = imageUrl,
        sourceName = sourceName,
        authorName = authorName,
        publishedAt = parseAtomDate(published),
        updatedAt = updated?.let {
            parseAtomDate(it)
        },
        categories = categories,
        isSaved = false
    )
}

private fun parseAtomDate(
    date: String
): Long {

    return try {
        OffsetDateTime
            .parse(date)
            .toInstant()
            .toEpochMilli()
    } catch (e: Exception) {
        0L
    }
}

private fun String?.toPlainText(): String {

    if (this.isNullOrBlank()) {
        return ""
    }

    return Html
        .fromHtml(
            this,
            Html.FROM_HTML_MODE_LEGACY
        )
        .toString()
        .replace(Regex("\\s+"), " ")
        .trim()
}