package com.thestackdigest.app.data.remote.atom

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import javax.inject.Inject

class AtomParser @Inject constructor() {

    fun parse(xml: String): List<AtomEntryDto> {

        val entries = mutableListOf<AtomEntryDto>()

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
                parser.name == "entry"
            ) {
                entries.add(
                    parseEntry(parser)
                )
            }

            eventType = parser.next()
        }

        return entries
    }

    private fun parseEntry(
        parser: XmlPullParser
    ): AtomEntryDto {

        var id = ""
        var title = ""
        var articleUrl = ""
        var authorName: String? = null
        var published = ""
        var updated: String? = null
        var contentHtml: String? = null
        var imageUrl: String? = null

        val categories = mutableListOf<String>()

        var insideAuthor = false

        var eventType = parser.next()

        while (
            !(eventType == XmlPullParser.END_TAG &&
                    parser.name == "entry")
        ) {

            when (eventType) {

                XmlPullParser.START_TAG -> {

                    when (parser.name) {

                        "id" -> {
                            id = parser.nextText()
                        }

                        "title" -> {
                            title = parser.nextText()
                        }

                        "published" -> {
                            published = parser.nextText()
                        }

                        "updated" -> {
                            updated = parser.nextText()
                        }

                        "content" -> {
                            contentHtml = parser.nextText()
                        }

                        "link" -> {

                            val rel = parser.getAttributeValue(
                                null,
                                "rel"
                            )

                            if (rel == "alternate") {
                                articleUrl =
                                    parser.getAttributeValue(
                                        null,
                                        "href"
                                    ).orEmpty()
                            }
                        }

                        "author" -> {
                            insideAuthor = true
                        }

                        "name" -> {
                            if (insideAuthor) {
                                authorName = parser.nextText()
                            }
                        }

                        "thumbnail" -> {
                            imageUrl =
                                parser.getAttributeValue(
                                    null,
                                    "url"
                                )
                        }

                        "category" -> {

                            parser.getAttributeValue(
                                null,
                                "term"
                            )?.let { category ->

                                categories.add(category)
                            }
                        }
                    }
                }

                XmlPullParser.END_TAG -> {

                    if (parser.name == "author") {
                        insideAuthor = false
                    }
                }
            }

            eventType = parser.next()
        }

        return AtomEntryDto(
            id = id,
            title = title,
            articleUrl = articleUrl,
            authorName = authorName,
            published = published,
            updated = updated,
            contentHtml = contentHtml,
            imageUrl = imageUrl,
            categories = categories
        )
    }
}