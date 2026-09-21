package com.thestackdigest.app.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.thestackdigest.app.R
import com.thestackdigest.app.domain.model.Article
import com.thestackdigest.app.ui.utils.formatRelativeTime


/*@Preview
@Composable
fun ArticleCardPreview() {
    ArticleCard()
}*/

@Composable
fun ArticleCard(article: Article, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // Main layout
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(12.dp),
        ) {
            // Left column
            ArticleInfo(
                article,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 12.dp)
            )

            // Right column
            ArticleThumbnail(
                article
            )
        }
    }
}

@Composable
fun ArticleInfo(article: Article, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        SourceInfo(article)

        //title
        Text(
            article.title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        //Description
        Text(
            article.description,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

    }
}

@Composable
fun SourceInfo(article: Article) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painterResource(
                getSourceIcon(article.sourceName)
            ),
            "",
            Modifier.size(36.dp)
        )

        Spacer(Modifier.width(4.dp))

        Column() {
            Text(
                article.sourceName,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            //Description
            Text(
                text = formatRelativeTime(article.publishedAt),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 0.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ArticleThumbnail(article: Article, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .width(90.dp)
            .fillMaxHeight(),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        AsyncImage(
            model = article.imageUrl,
            contentDescription = article.title,
            modifier = Modifier
                .size(90.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop,
            error = painterResource(R.drawable.android_image),
            fallback = painterResource(R.drawable.android_image)
        )
        Icon(
            painterResource(R.drawable.ic_bookmark),
            contentDescription = "Save article",
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@DrawableRes
fun getSourceIcon(sourceName: String): Int {
    return when (sourceName) {
        "Android Developers" -> R.drawable.ic_android_small_logo
        "Kotlin Blog" -> R.drawable.ic_kotlin_small_logo
        else -> R.drawable.ic_android_small_logo
    }
}
