package com.thestackdigest.app.domain.repository

import com.thestackdigest.app.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface FeedRepository {
    //continuously read articles from Room
    fun observeArticles(): Flow<List<Article>>

    // get single article on basis of id
    fun observeArticle(
        articleId: String
    ): Flow<Article?>

    //continuously read saved articles from Room
    fun observeSavedArticles(): Flow<List<Article>>

    //fetch fresh articles from internet and save them to Room
    suspend fun refreshArticles()

    // save article
    suspend fun setArticleSaved(
        articleId: String,
        isSaved: Boolean
    )
}