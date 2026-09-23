package com.thestackdigest.app.data.remote

import com.thestackdigest.app.data.remote.atom.AtomParser
import com.thestackdigest.app.data.remote.atom.toArticle
import com.thestackdigest.app.data.remote.rss.RssParser
import com.thestackdigest.app.data.remote.rss.toArticle
import com.thestackdigest.app.domain.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FeedRemoteDataSource @Inject constructor(
    private val feedApi: FeedApi,
    private val rssParser: RssParser,
    private val atomParser: AtomParser
) {

    suspend fun fetchRssArticles(
        source: FeedSource
    ): List<Article>  = withContext(Dispatchers.IO) {

        val xml = feedApi
            .getFeed(source.url)
            .use { responseBody ->
                responseBody.string()
            }

        val rssItems = rssParser.parse(xml)

         rssItems.map { item ->
            item.toArticle(
                sourceName = source.name
            )
        }
    }

    suspend fun fetchAtomArticles(
        source: FeedSource
    ): List<Article> = withContext(Dispatchers.IO) {

        val xml = feedApi
            .getFeed(source.url)
            .use { responseBody ->
                responseBody.string()
            }

        val atomEntries = atomParser.parse(xml)

         atomEntries.map { entry ->
            entry.toArticle(
                sourceName = source.name
            )
        }
    }
}