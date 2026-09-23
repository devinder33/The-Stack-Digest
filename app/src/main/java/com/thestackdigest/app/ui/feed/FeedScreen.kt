package com.thestackdigest.app.ui.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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

@Composable
fun FeedRoute(
    paddingValues: PaddingValues,
     viewModel: FeedViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    FeedScreen(
        paddingValues,
        uiState = uiState,
        onCategorySelected = viewModel::onCategorySelected
    )
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun FeedScreenPreview() {

    FeedScreen(
        paddingValues = PaddingValues(16.dp),
        uiState = FeedUiState(
            articles = fakeArticles,
            selectedCategory = "All"
        ),
        onCategorySelected = {}
    )
}

@Composable
fun FeedScreen(
    paddingValues: PaddingValues,
    uiState: FeedUiState,
    onCategorySelected: (String) -> Unit
) {
    val categories = listOf(
        "All",
        "Android",
        "Kotlin",
        "AI",
        "Web"
    )

    Column(
        modifier = Modifier
            .padding(paddingValues)
    ) {

        AppBar("The Stack Digest", true)

        Chips(
            categories = categories,
            selectedCategory = uiState.selectedCategory,
            onCategoryClicked = onCategorySelected
        )

        when {

            uiState.isLoading &&
                    uiState.articles.isEmpty() -> {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.errorMessage != null &&
                    uiState.articles.isEmpty() -> {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = uiState.errorMessage
                            ?: "Something went wrong"
                    )
                }
            }

            else -> {

                Articles(
                    filteredList = uiState.articles
                )
            }
        }

    }
}

@Composable
fun Chips(categories: List<String>, selectedCategory: String, onCategoryClicked: (String) -> Unit) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        contentPadding = PaddingValues(horizontal = 8.dp),
        horizontalArrangement = Arrangement.Absolute.SpaceEvenly
    ) {
        items(
            categories
        ) { title ->
            SingleChip(
                chipTitle = title,
                isSelected = title == selectedCategory,
                onCategoryClicked = {
                    onCategoryClicked(title)
                }
            )
        }
    }
}

@Composable
fun SingleChip(chipTitle: String, isSelected: Boolean, onCategoryClicked: (String) -> Unit) {
    Box(
        Modifier
            .background(
                color = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.surfaceVariant
                },
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 18.dp, vertical = 8.dp)
            .clickable{
                onCategoryClicked(chipTitle)
            }
    ) {
        Text(
            chipTitle,
            style = MaterialTheme.typography.bodyMedium,
            color = if (isSelected) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            }
        )
    }
}

@Composable
fun Articles(filteredList: List<Article>, modifier: Modifier = Modifier) {
    LazyColumn(

        modifier = modifier.padding(top = 8.dp, bottom = 8.dp)
    ) {
        items(
            items = filteredList,
            key = { article -> article.id }
        ) { article ->

            ArticleCard(article = article)
        }
    }
}