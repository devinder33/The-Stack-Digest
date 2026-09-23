package com.thestackdigest.app.data.repository

import com.thestackdigest.app.data.remote.FeedFormat
import com.thestackdigest.app.data.remote.FeedRemoteDataSource
import com.thestackdigest.app.data.remote.feedSources
import com.thestackdigest.app.domain.model.Article
import com.thestackdigest.app.domain.repository.FeedRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class FeedRepositoryImpl @Inject constructor(
    private val remoteDataSource: FeedRemoteDataSource,
) : FeedRepository {

    override suspend fun getArticles(): List<Article> = coroutineScope {

        feedSources.map { source ->

            async {

                try {
                    when (source.format) {

                        FeedFormat.RSS -> {
                            remoteDataSource.fetchRssArticles(source)
                        }

                        FeedFormat.ATOM -> {
                            remoteDataSource.fetchAtomArticles(source)
                        }
                    }

                } catch (e: CancellationException) {
                    throw e
                } catch (e: Exception) {
                    emptyList()
                }
            }
        }
            .awaitAll()
            .flatten()
            .sortedByDescending {
                it.publishedAt
            }
    }
}