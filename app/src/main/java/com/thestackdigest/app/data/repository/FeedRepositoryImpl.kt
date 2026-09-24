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

    override suspend fun refreshArticles() = coroutineScope {

        val results = feedSources
            .map { source ->

                async {

                    try {

                        Result.success(
                            when (source.format) {

                                FeedFormat.RSS -> {
                                    remoteDataSource.fetchRssArticles(source)
                                }

                                FeedFormat.ATOM -> {
                                    remoteDataSource.fetchAtomArticles(source)
                                }
                            }
                        )

                    } catch (e: CancellationException) {
                        throw e

                    } catch (e: Exception) {
                        Result.failure(e)
                    }
                }
            }
            .awaitAll()

        val articles = results
            .mapNotNull { result ->
                result.getOrNull()
            }
            .flatten()

        val allFailed =
            results.isNotEmpty() &&
                    results.all { result ->
                        result.isFailure
                    }

        if (allFailed) {
            throw results
                .firstNotNullOf { result ->
                    result.exceptionOrNull()
                }
        }

        val savedArticleIds =
            articleDao
                .getSavedArticleIds()
                .toSet()

        val entities = articles.map { article ->

            article
                .copy(
                    isSaved = article.id in savedArticleIds
                )
                .toEntity()
        }

        articleDao.upsertArticles(entities)
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
}