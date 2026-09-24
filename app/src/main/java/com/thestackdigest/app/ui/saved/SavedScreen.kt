package com.thestackdigest.app.ui.saved

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thestackdigest.app.domain.model.Article
import com.thestackdigest.app.ui.components.AppBar
import com.thestackdigest.app.ui.components.ArticleCard
import com.thestackdigest.app.ui.feed.fakeArticles

@Composable
fun SavedRoute(
    paddingValues: PaddingValues,
    viewModel: SavedViewModel = hiltViewModel()
) {

    val savedArticles by
    viewModel.savedArticles.collectAsStateWithLifecycle()

    SavedScreen(
        paddingValues = paddingValues,
        articles = savedArticles,
        onBookmarkClicked = viewModel::onBookmarkClicked
    )
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun SavedScreenPreview() {

    SavedScreen(
        paddingValues = PaddingValues(16.dp),
        articles = fakeArticles.filter { article ->
            article.isSaved
        },
        onBookmarkClicked = {}
    )
}

@Composable
fun SavedScreen(
    paddingValues: PaddingValues,
    articles: List<Article>,
    onBookmarkClicked: (Article) -> Unit
) {

    androidx.compose.foundation.layout.Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {

        AppBar(
            title = "Saved",
            isShowSearch = false
        )

        if (articles.isEmpty()) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No saved articles yet"
                )
            }

        } else {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    vertical = 8.dp
                )
            ) {

                items(
                    items = articles,
                    key = { article ->
                        article.id
                    }
                ) { article ->

                    ArticleCard(
                        article = article,
                        onBookmarkClicked = {
                            onBookmarkClicked(article)
                        }
                    )
                }
            }
        }
    }
}