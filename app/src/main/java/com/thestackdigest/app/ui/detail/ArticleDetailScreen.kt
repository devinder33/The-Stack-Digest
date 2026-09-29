package com.thestackdigest.app.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ArrowBackIos
import androidx.compose.material.icons.automirrored.outlined.ScreenShare
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.OpenInNew
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.Image
import coil3.compose.AsyncImage
import com.thestackdigest.app.R
import com.thestackdigest.app.domain.model.Article
import com.thestackdigest.app.ui.components.getSourceIcon
import com.thestackdigest.app.ui.feed.FeedScreen
import com.thestackdigest.app.ui.feed.FeedUiState
import com.thestackdigest.app.ui.feed.fakeArticles
import com.thestackdigest.app.ui.utils.formatRelativeTime

@Composable
fun ArticleDetailRoute(
    paddingValues: PaddingValues,
    onBackClick: () -> Unit,
    viewModel: ArticleDetailViewModel = hiltViewModel()
) {

    val article by
    viewModel.article.collectAsStateWithLifecycle()

    val currentArticle = article

    if (currentArticle == null) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

        return
    }

    ArticleDetailScreen(
        paddingValues = paddingValues,
        article = currentArticle,
        onBackClick = onBackClick,
        onBookmarkClick = {
            viewModel.onBookmarkClicked(currentArticle)
        }
    )
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun ArticleDetailScreenPreview() {

    val article = fakeArticles.first()

    ArticleDetailScreen(
        paddingValues = PaddingValues(16.dp),
        article = article,
        onBackClick = {},
        onBookmarkClick = {}
    )
}

@Composable
fun ArticleDetailScreen(
    paddingValues: PaddingValues,
    article: Article,
    onBackClick: () -> Unit,
    onBookmarkClick: () -> Unit
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentPadding = PaddingValues(
            bottom = 24.dp
        )
    ) {

        item {

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            AppBarDetail(
                article = article,
                onBackClick = onBackClick,
                onBookmarkClick = onBookmarkClick
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )
        }

        item {

            BannerImage(
                article = article
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )
        }

        item {

            Column(
                modifier = Modifier.padding(
                    horizontal = 24.dp
                )
            ) {

                SourceInfo(article)

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Text(
                    text = article.title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                PublishedTimeInfo(article)

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                DescriptionText(article)

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                BrowserButton()

                Spacer(
                    modifier = Modifier.height(24.dp)
                )
            }
        }
    }
}

@Composable
fun AppBarDetail(
    article: Article,
    onBackClick: () -> Unit,
    onBookmarkClick: () -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {

        IconButton(
            onClick = onBackClick
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                contentDescription = "Back"
            )
        }

        Spacer(
            modifier = Modifier.weight(1f)
        )

        IconButton(
            onClick = onBookmarkClick
        ) {

            Icon(
                imageVector = if (article.isSaved) {
                    Icons.Filled.Bookmark
                } else {
                    Icons.Outlined.BookmarkBorder
                },
                contentDescription = if (article.isSaved) {
                    "Remove bookmark"
                } else {
                    "Save article"
                }
            )
        }

        IconButton(
            onClick = {
                // Share next
            }
        ) {

            Icon(
                imageVector = Icons.Outlined.Share,
                contentDescription = "Share article"
            )
        }
    }
}

@Composable
fun BannerImage(article: Article, modifier: Modifier = Modifier) {
    AsyncImage(
        model = article.imageUrl,
        contentDescription = "image",
        modifier = Modifier
            .aspectRatio(16f / 9f)
            .clip(RoundedCornerShape(8.dp)),
        contentScale = ContentScale.FillBounds,
        error = painterResource(R.drawable.android_image),
        fallback = painterResource(R.drawable.android_image)
    )
}

@Composable
fun SourceInfo(article: Article) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Image(
            painterResource(
                getSourceIcon(article.sourceName)
            ),
            "",
            Modifier.size(24.dp)
        )

        Spacer(Modifier.width(10.dp))

        Text(
            text = article.sourceName,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun PublishedTimeInfo(article: Article) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

        // published at full date
        Text(
            text = formatRelativeTime(article.publishedAt),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .size(5.dp)
                .background(
                    color = MaterialTheme.colorScheme.onSurface,
                    shape = CircleShape
                )
        )

        Text(
            article.sourceName,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun DescriptionText(article: Article) {
    Text(
        article.description,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
fun BrowserButton() {
    Button(
        onClick = {},
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(

        ) {
            Icon(
                imageVector = Icons.Outlined.OpenInNew,
                contentDescription = "back arrow"
            )

            Spacer(Modifier.width(8.dp))

            Text(
                "Open in Browser",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.inverseOnSurface
            )

        }
    }
}
