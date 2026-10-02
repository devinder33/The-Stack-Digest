package com.thestackdigest.app

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.thestackdigest.app.data.notification.ArticleNotifier
import com.thestackdigest.app.data.worker.FeedRefreshScheduler
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class StackDigestApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var articleNotifier: ArticleNotifier

    override val workManagerConfiguration: Configuration
        get() =
            Configuration.Builder()
                .setWorkerFactory(workerFactory)
                .build()

    override fun onCreate() {
        super.onCreate()

        articleNotifier.createNotificationChannel()

        FeedRefreshScheduler.schedule(this)

        //articleNotifier.showTestNotification()

        //FeedRefreshScheduler.runOnceForTesting(this)
    }
}