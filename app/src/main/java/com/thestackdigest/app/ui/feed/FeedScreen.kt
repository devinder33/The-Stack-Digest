package com.thestackdigest.app.ui.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thestackdigest.app.ui.components.AppBar
import com.thestackdigest.app.ui.components.ArticleCard

@Composable
fun FeedRoute(
    paddingValues: PaddingValues
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
fun FeedScreen(paddingValues: PaddingValues)  {
    Column(modifier = Modifier
        .padding(paddingValues)) {

        AppBar("The Stack Digest", true)

        Chips()

        Articles()

    }
}

@Composable
fun Chips() {
    LazyRow(
        modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
        contentPadding = PaddingValues(horizontal = 8.dp),
//        horizontalArrangement = Arrangement.spacedBy(8.dp)
        horizontalArrangement = Arrangement.Absolute.SpaceEvenly
    ) {
        items(
            listOf("All", "Android", "Kotlin", "AI", "Web")
        ) { title ->
            SingleChip(title)
        }
    }
}

@Composable
fun SingleChip(chipTitle: String) {
    Box(
        Modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 18.dp, vertical = 8.dp)
    ) {
        Text(
            chipTitle,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun Articles(modifier: Modifier = Modifier) {
    LazyColumn(

        modifier = modifier.padding(top = 8.dp, bottom = 8.dp)
    ) {
        items(5) {
            ArticleCard()
        }
    }
}