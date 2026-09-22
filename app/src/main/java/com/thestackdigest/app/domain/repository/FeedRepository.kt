package com.thestackdigest.app.domain.repository

import com.thestackdigest.app.domain.model.Article

interface FeedRepository {
    suspend fun getArticles(): List<Article>
}