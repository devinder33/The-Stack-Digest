package com.thestackdigest.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleEntity(

    @PrimaryKey
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

    val isSaved: Boolean
)