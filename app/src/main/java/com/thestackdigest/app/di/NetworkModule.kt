package com.thestackdigest.app.di

import com.thestackdigest.app.data.remote.FeedApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://example.com/")
            .build()
    }

    @Provides
    @Singleton
    fun provideFeedApi(
        retrofit: Retrofit
    ): FeedApi {
        return retrofit.create(FeedApi::class.java)
    }
}