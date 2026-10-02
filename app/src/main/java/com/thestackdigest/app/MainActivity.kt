package com.thestackdigest.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.thestackdigest.app.ui.StackDigestRoot
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var notificationArticleId
            by mutableStateOf<String?>(null)

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        notificationArticleId =
            consumeNotificationArticleId(intent)

        setContent {

            StackDigestRoot(
                notificationArticleId =
                    notificationArticleId,
                onNotificationArticleHandled = {
                    notificationArticleId = null
                }
            )
        }
    }

    override fun onNewIntent(
        intent: Intent
    ) {
        super.onNewIntent(intent)

        setIntent(intent)

        notificationArticleId =
            consumeNotificationArticleId(intent)
    }

    private fun consumeNotificationArticleId(
        intent: Intent
    ): String? {

        val articleId =
            intent.getStringExtra(
                EXTRA_ARTICLE_ID
            )

        intent.removeExtra(
            EXTRA_ARTICLE_ID
        )

        return articleId
    }

    companion object {

        const val EXTRA_ARTICLE_ID =
            "extra_article_id"
    }
}