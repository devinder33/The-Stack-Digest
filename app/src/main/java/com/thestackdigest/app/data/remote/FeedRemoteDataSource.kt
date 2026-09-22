package com.thestackdigest.app.data.remote

import com.thestackdigest.app.data.remote.rss.RssParser
import com.thestackdigest.app.data.remote.rss.toArticle
import com.thestackdigest.app.domain.model.Article
import javax.inject.Inject

class FeedRemoteDataSource @Inject constructor(
    private val feedApi: FeedApi,
    private val rssParser: RssParser
) {

    suspend fun fetchRssArticles(
        source: FeedSource
    ): List<Article> {

        val xml = feedApi
            .getFeed(source.url)
            .use { responseBody ->
                responseBody.string()
            }

        val rssItems = rssParser.parse(xml)

        return rssItems.map { item ->
            item.toArticle(
                sourceName = source.name
            )
        }
    }
}