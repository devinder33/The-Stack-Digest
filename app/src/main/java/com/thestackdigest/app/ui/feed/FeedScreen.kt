package com.thestackdigest.app.ui.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thestackdigest.app.domain.model.Article
import com.thestackdigest.app.ui.components.AppBar
import com.thestackdigest.app.ui.components.ArticleCard

@Composable
fun FeedRoute(
    paddingValues: PaddingValues,
    // viewModel: FeedViewModel = hiltViewModel()
) {
    FeedScreen(paddingValues)
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun FeedScreenPreview() {
    FeedScreen(PaddingValues(16.dp))
}

@Composable
fun FeedScreen(paddingValues: PaddingValues) {
    val categories = listOf(
        "All",
        "Android",
        "Kotlin",
        "AI",
        "Web"
    )
    var selectedCategory by rememberSaveable {
        mutableStateOf("All")
    }

    val filteredArticles = if (selectedCategory == "All") {
        fakeArticles
    } else {
        fakeArticles.filter { article ->
            selectedCategory in article.categories
        }
    }

    Column(
        modifier = Modifier
            .padding(paddingValues)
    ) {

        AppBar("The Stack Digest", true)

        Chips(
            categories = categories,
            selectedCategory = selectedCategory,
            onCategoryClicked = { category ->
                selectedCategory = category
            }
        )

        Articles(filteredArticles)

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