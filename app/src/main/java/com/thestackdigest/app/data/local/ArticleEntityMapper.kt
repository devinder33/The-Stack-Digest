package com.thestackdigest.app.data.local

import com.thestackdigest.app.domain.model.Article

fun Article.toEntity(): ArticleEntity {
    return ArticleEntity(
        id = id,
        title = title,
        description = description,
        articleUrl = articleUrl,
        imageUrl = imageUrl,
        sourceName = sourceName,
        authorName = authorName,
        publishedAt = publishedAt,
        updatedAt = updatedAt,
        categories = categories,
        isSaved = isSaved
    )
}

fun ArticleEntity.toArticle(): Article {
    return Article(
        id = id,
        title = title,
        description = description,
        articleUrl = articleUrl,
        imageUrl = imageUrl,
        sourceName = sourceName,
        authorName = authorName,
        publishedAt = publishedAt,
        updatedAt = updatedAt,
        categories = categories,
        isSaved = isSaved
    )
}