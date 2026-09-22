package com.thestackdigest.app.data.remote.rss

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import javax.inject.Inject

class RssParser @Inject constructor() {

    fun parse(xml: String): List<RssItemDto> {

        val articles = mutableListOf<RssItemDto>()

        val parser = XmlPullParserFactory
            .newInstance()
            .apply {
                isNamespaceAware = true
            }
            .newPullParser()

        parser.setInput(StringReader(xml))

        var eventType = parser.eventType

        while (eventType != XmlPullParser.END_DOCUMENT) {

            if (
                eventType == XmlPullParser.START_TAG &&
                parser.name == "item"
            ) {
                articles.add(
                    parseItem(parser)
                )
            }

            eventType = parser.next()
        }

        return articles
    }

    private fun parseItem(
        parser: XmlPullParser
    ): RssItemDto {

        var guid = ""
        var title = ""
        var link = ""
        var creator: String? = null
        var pubDate = ""
        var featuredImage: String? = null
        var description: String? = null
        var contentHtml: String? = null

        val categories = mutableListOf<String>()

        var eventType = parser.next()

        while (
            !(eventType == XmlPullParser.END_TAG &&
                    parser.name == "item")
        ) {

            if (eventType == XmlPullParser.START_TAG) {

                when (parser.name) {

                    "guid" -> {
                        guid = parser.nextText()
                    }

                    "title" -> {
                        title = parser.nextText()
                    }

                    "link" -> {
                        link = parser.nextText()
                    }

                    "creator" -> {
                        creator = parser.nextText()
                    }

                    "pubDate" -> {
                        pubDate = parser.nextText()
                    }

                    "featuredImage" -> {
                        featuredImage = parser.nextText()
                    }

                    "description" -> {
                        description = parser.nextText()
                    }

                    "encoded" -> {
                        contentHtml = parser.nextText()
                    }

                    "category" -> {
                        categories.add(
                            parser.nextText()
                        )
                    }
                }
            }

            eventType = parser.next()
        }

        return RssItemDto(
            guid = guid,
            title = title,
            link = link,
            creator = creator,
            pubDate = pubDate,
            featuredImage = featuredImage,
            description = description,
            contentHtml = contentHtml,
            categories = categories
        )
    }
}