package com.thestackdigest.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thestackdigest.app.R


@Preview
@Composable
fun ArticleCardPreview() {
    ArticleCard()
}

@Composable
fun ArticleCard(modifier: Modifier = Modifier) {
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
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 12.dp)
            )

            // Right column
            ArticleThumbnail()
        }
    }
}

@Composable
fun ArticleInfo(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        AuthorDetail()

        //title
        Text(
            "What's new in Android 15 for developers",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        //Description
        Text(
            "n fact, inserting any fantasy text or a famous text, be it a poem, a speech, a literary passage, a song's text, etc., our text generator will provide the random extraction of terms and steps to compose your own exclusive Lorem Ipsum.",
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

    }
}

@Composable
fun AuthorDetail() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painterResource(R.drawable.ic_android), "",
            Modifier.size(36.dp)
        )

        Spacer(Modifier.width(4.dp))

        Column() {
            Text(
                "Android Developers",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            //Description
            Text(
                "2 hours ago", style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 0.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ArticleThumbnail() {
    Column(
        modifier = Modifier
            .width(90.dp)
            .fillMaxHeight(),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            modifier = Modifier
                .padding(4.dp)
                .size(90.dp)
                .clip(RoundedCornerShape(8.dp)),
            painter = painterResource(R.drawable.android_image),
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
        Icon(
            painterResource(R.drawable.ic_bookmark),
            contentDescription = "Save article",
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
