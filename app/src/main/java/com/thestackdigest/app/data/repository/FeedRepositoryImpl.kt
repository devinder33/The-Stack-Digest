package com.thestackdigest.app.data.repository

import com.thestackdigest.app.data.local.ArticleDao
import com.thestackdigest.app.data.local.toArticle
import com.thestackdigest.app.data.local.toEntity
import com.thestackdigest.app.data.remote.FeedFormat
import com.thestackdigest.app.data.remote.FeedRemoteDataSource
import com.thestackdigest.app.data.remote.feedSources
import com.thestackdigest.app.domain.model.Article
import com.thestackdigest.app.domain.repository.FeedRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    private val remoteDataSource: FeedRemoteDataSource,
    private val articleDao: ArticleDao
) : FeedRepository {

    override fun observeArticles(): Flow<List<Article>> {

        return articleDao
            .observeArticles()
            .map { entities ->

                entities.map { entity ->
                    entity.toArticle()
                }
            }
    }

    override suspend fun refreshArticles(): List<Article> =
        coroutineScope {

            val existingArticleIds =
                articleDao
                    .getAllArticleIds()
                    .toSet()

            val results =
                feedSources
                    .map { source ->

                        async {

                            try {

                                val articles =
                                    when (source.format) {

                                        FeedFormat.RSS ->
                                            remoteDataSource
                                                .fetchRssArticles(source)

                                        FeedFormat.ATOM ->
                                            remoteDataSource
                                                .fetchAtomArticles(source)
                                    }

                                Result.success(articles)

                            } catch (e: CancellationException) {

                                throw e

                            } catch (e: Exception) {

                                Result.failure(e)
                            }
                        }
                    }
                    .awaitAll()

            if (
                results.isNotEmpty() &&
                results.all { it.isFailure }
            ) {

                throw results
                    .first()
                    .exceptionOrNull()
                    ?: Exception("Unable to refresh feeds")
            }

            val fetchedArticles =
                results
                    .mapNotNull {
                        it.getOrNull()
                    }
                    .flatten()

            val newArticles =
                if (existingArticleIds.isEmpty()) {

                    emptyList()

                } else {

                    fetchedArticles.filter { article ->
                        article.id !in existingArticleIds
                    }
                }

            val savedArticleIds =
                articleDao
                    .getSavedArticleIds()
                    .toSet()

            val entities =
                fetchedArticles.map { article ->

                    article
                        .copy(
                            isSaved =
                                article.id in savedArticleIds
                        )
                        .toEntity()
                }

            articleDao.upsertArticles(entities)

            newArticles
        }

    override suspend fun setArticleSaved(
        articleId: String,
        isSaved: Boolean
    ) {

        articleDao.updateSavedState(
            articleId = articleId,
            isSaved = isSaved
        )
    }

    override fun observeSavedArticles(): Flow<List<Article>> {

        return articleDao
            .observeSavedArticles()
            .map { entities ->

                entities.map { entity ->
                    entity.toArticle()
                }
            }
    }

    override fun observeArticle(
        articleId: String
    ): Flow<Article?> {

        return articleDao
            .observeArticle(articleId)
            .map { entity ->
                entity?.toArticle()
            }
    }
}