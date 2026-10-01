package com.thestackdigest.app.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.thestackdigest.app.domain.repository.FeedRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class FeedRefreshWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParameters: WorkerParameters,
    private val repository: FeedRepository
) : CoroutineWorker(
    appContext,
    workerParameters
) {

    override suspend fun doWork(): Result {

        return try {

            repository.refreshArticles()

            Result.success()

        } catch (e: Exception) {

            Result.retry()
        }
    }
}