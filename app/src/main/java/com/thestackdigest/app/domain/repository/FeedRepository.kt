package com.thestackdigest.app.domain.repository

import com.thestackdigest.app.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface FeedRepository {
    //continuously read articles from Room
    fun observeArticles(): Flow<List<Article>>

    //fetch fresh articles from internet and save them to Room
    suspend fun refreshArticles()

    // save article
    suspend fun setArticleSaved(
        articleId: String,
        isSaved: Boolean
    )
}