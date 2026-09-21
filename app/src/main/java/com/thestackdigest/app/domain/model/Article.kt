package com.thestackdigest.app.domain.model

data class Article(
    val id: String,
    val title: String,
    val description: String,
    val articleUrl: String,
    val imageUrl: String?,
    val sourceName: String,
    val authorName: String?,
    val publishedAt: Long,
    val updatedAt: Long?,
    val categories: List<String>,
    val isSaved: Boolean = false
)