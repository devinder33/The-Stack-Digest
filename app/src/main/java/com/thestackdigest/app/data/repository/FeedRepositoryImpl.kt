package com.thestackdigest.app.data.repository

import com.thestackdigest.app.data.remote.FeedFormat
import com.thestackdigest.app.data.remote.FeedRemoteDataSource
import com.thestackdigest.app.data.remote.feedSources
import com.thestackdigest.app.domain.model.Article
import com.thestackdigest.app.domain.repository.FeedRepository
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    private val remoteDataSource: FeedRemoteDataSource
) : FeedRepository {

    override suspend fun getArticles(): List<Article> {

        val rssSources = feedSources.filter {
            it.format == FeedFormat.RSS
        }

        return rssSources.flatMap { source ->
            remoteDataSource.fetchRssArticles(source)
        }
    }
}