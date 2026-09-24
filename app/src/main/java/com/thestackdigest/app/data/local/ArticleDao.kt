package com.thestackdigest.app.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Query(
        """
        SELECT * FROM articles
        ORDER BY publishedAt DESC
        """
    )
    fun observeArticles(): Flow<List<ArticleEntity>>

    @Query(
        """
        SELECT * FROM articles
        WHERE isSaved = 1
        ORDER BY publishedAt DESC
        """
    )
    fun observeSavedArticles(): Flow<List<ArticleEntity>>

    @Upsert
    suspend fun upsertArticles(
        articles: List<ArticleEntity>
    )

    @Query(
        """
        SELECT id
        FROM articles
        WHERE isSaved = 1
        """
    )
    suspend fun getSavedArticleIds(): List<String>

    @Query(
        """
        UPDATE articles
        SET isSaved = :isSaved
        WHERE id = :articleId
        """
    )
    suspend fun updateSavedState(
        articleId: String,
        isSaved: Boolean
    )
}