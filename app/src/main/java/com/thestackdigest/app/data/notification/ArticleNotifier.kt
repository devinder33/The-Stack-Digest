package com.thestackdigest.app.data.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.thestackdigest.app.MainActivity
import com.thestackdigest.app.R
import com.thestackdigest.app.domain.model.Article
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleNotifier @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun createNotificationChannel() {

        val notificationManager =
            context.getSystemService(
                NotificationManager::class.java
            )

        val channel = NotificationChannel(
            CHANNEL_ID,
            "New articles",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description =
                "Notifications for new articles from The Stack Digest"
        }

        notificationManager.createNotificationChannel(
            channel
        )
    }

    fun showNewArticlesNotification(
        newArticles: List<Article>
    ) {

        if (newArticles.isEmpty()) {
            return
        }

        if (!hasNotificationPermission()) {
            return
        }

        val articleToOpen =
            newArticles.maxByOrNull {
                it.publishedAt
            } ?: return

        val title =
            if (newArticles.size == 1) {
                "New article"
            } else {
                "${newArticles.size} new articles"
            }

        val message =
            if (newArticles.size == 1) {

                articleToOpen.title

            } else {

                newArticles
                    .take(3)
                    .joinToString(" • ") {
                        it.title
                    }
            }

        val notification =
            NotificationCompat.Builder(
                context,
                CHANNEL_ID
            )
                .setSmallIcon(
                    R.drawable.ic_notification
                )
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(
                    NotificationCompat
                        .BigTextStyle()
                        .bigText(message)
                )
                .setContentIntent(
                    createArticlePendingIntent(
                        articleToOpen
                    )
                )
                .setPriority(
                    NotificationCompat.PRIORITY_DEFAULT
                )
                .setAutoCancel(true)
                .build()

        NotificationManagerCompat
            .from(context)
            .notify(
                NEW_ARTICLES_NOTIFICATION_ID,
                notification
            )
    }

    private fun createArticlePendingIntent(
        article: Article
    ): PendingIntent {

        val intent =
            Intent(
                context,
                MainActivity::class.java
            ).apply {

                flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                            Intent.FLAG_ACTIVITY_SINGLE_TOP

                putExtra(
                    MainActivity.EXTRA_ARTICLE_ID,
                    article.id
                )
            }

        return PendingIntent.getActivity(
            context,
            article.id.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or
                    PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun hasNotificationPermission(): Boolean {

        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    }

    companion object {

        private const val CHANNEL_ID =
            "new_articles"

        private const val NEW_ARTICLES_NOTIFICATION_ID =
            1001
    }
}