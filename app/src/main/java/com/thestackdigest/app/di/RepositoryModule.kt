package com.thestackdigest.app.di

import com.thestackdigest.app.data.repository.FeedRepositoryImpl
import com.thestackdigest.app.domain.repository.FeedRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindFeedRepository(
        implementation: FeedRepositoryImpl
    ): FeedRepository
}