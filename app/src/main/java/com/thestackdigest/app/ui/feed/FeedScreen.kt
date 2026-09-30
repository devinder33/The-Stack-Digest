package com.thestackdigest.app.ui.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
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
    onArticleClicked: (Article) -> Unit,
    viewModel: FeedViewModel = hiltViewModel()
) {

    val uiState by
    viewModel.uiState.collectAsStateWithLifecycle()

    FeedScreen(
        paddingValues = paddingValues,
        uiState = uiState,
        onCategorySelected = viewModel::onCategorySelected,
        onBookmarkClicked = viewModel::onBookmarkClicked,
        onArticleClicked = onArticleClicked,
        onSearchClick = viewModel::onSearchClick,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        onSearchClose = viewModel::onSearchClose,
        onRefresh = viewModel::onRefresh
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    paddingValues: PaddingValues,
    uiState: FeedUiState,
    onCategorySelected: (String) -> Unit,
    onBookmarkClicked: (Article) -> Unit,
    onArticleClicked: (Article) -> Unit,
    onSearchClick: () -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    onSearchClose: () -> Unit,
    onRefresh: () -> Unit
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
            .fillMaxSize()
            .padding(paddingValues)
    ) {

        AppBar(
            title = "The Stack Digest",
            showSearch = !uiState.isSearchActive,
            onSearchClick = onSearchClick
        )

        if (uiState.isSearchActive) {

            FeedSearchBar(
                query = uiState.searchQuery,
                onQueryChanged = onSearchQueryChanged,
                onCloseClick = onSearchClose
            )
        }

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

            uiState.searchQuery.isNotBlank() &&
                    uiState.articles.isEmpty() -> {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No articles found"
                    )
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
                        text = "Couldn't load articles"
                    )
                }
            }

            else -> {

                PullToRefreshBox(
                    isRefreshing = uiState.isLoading &&
                                uiState.articles.isNotEmpty(),
                    onRefresh = onRefresh,
                    modifier = Modifier.weight(1f)
                ) {

                    Articles(
                        articles = uiState.articles,
                        onBookmarkClicked = onBookmarkClicked,
                        onArticleClicked = onArticleClicked,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun FeedSearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onCloseClick: () -> Unit
) {

    val focusRequester = remember {
        FocusRequester()
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {

        focusRequester.requestFocus()

        keyboardController?.show()
    }

    OutlinedTextField(
        value = query,
        onValueChange = onQueryChanged,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
            .focusRequester(focusRequester),
        placeholder = {
            Text(
                text = "Search articles"
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = null
            )
        },
        trailingIcon = {
            IconButton(
                onClick = onCloseClick
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close search"
                )
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
fun Chips(
    categories: List<String>,
    selectedCategory: String,
    onCategoryClicked: (String) -> Unit
) {

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        contentPadding = PaddingValues(
            horizontal = 8.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        items(
            items = categories
        ) { title ->

            SingleChip(
                chipTitle = title,
                isSelected = title == selectedCategory,
                onCategoryClicked = onCategoryClicked
            )
        }
    }
}

@Composable
fun SingleChip(
    chipTitle: String,
    isSelected: Boolean,
    onCategoryClicked: (String) -> Unit
) {

    Box(
        modifier = Modifier
            .background(
                color = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.surfaceVariant
                },
                shape = RoundedCornerShape(16.dp)
            )
            .clickable {
                onCategoryClicked(chipTitle)
            }
            .padding(
                horizontal = 18.dp,
                vertical = 8.dp
            )
    ) {

        Text(
            text = chipTitle,
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
fun Articles(
    articles: List<Article>,
    onBookmarkClicked: (Article) -> Unit,
    onArticleClicked: (Article) -> Unit,
    modifier: Modifier = Modifier
) {

    LazyColumn(
        modifier = modifier.padding(
            top = 8.dp,
            bottom = 8.dp
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
                },
                modifier = Modifier.clickable {
                    onArticleClicked(article)
                }
            )
        }
    }
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
        onCategorySelected = {},
        onBookmarkClicked = {},
        onArticleClicked = {},
        onSearchClick = {},
        onSearchQueryChanged = {},
        onSearchClose = {},
        onRefresh = {}
    )
}