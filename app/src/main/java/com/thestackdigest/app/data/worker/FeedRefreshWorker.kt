package com.thestackdigest.app.data.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.thestackdigest.app.data.notification.ArticleNotifier
import com.thestackdigest.app.domain.repository.FeedRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class FeedRefreshWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParameters: WorkerParameters,
    private val repository: FeedRepository,
    private val articleNotifier: ArticleNotifier
) : CoroutineWorker(
    appContext,
    workerParameters
) {

    override suspend fun doWork(): Result {

        Log.d(
            "FeedRefreshWorker",
            "Worker started"
        )

        return try {

            val newArticles =
                repository.refreshArticles()

            Log.d(
                "FeedRefreshWorker",
                "New articles found: ${newArticles.size}"
            )

            if (newArticles.isNotEmpty()) {

                articleNotifier
                    .showNewArticlesNotification(
                        newArticles
                    )
            }

            Log.d(
                "FeedRefreshWorker",
                "Feed refresh successful"
            )

            Result.success()

        } catch (e: Exception) {

            Log.e(
                "FeedRefreshWorker",
                "Feed refresh failed",
                e
            )

            Result.retry()
        }
    }
}