package com.thestackdigest.app.di

import android.content.Context
import androidx.room.Room
import com.thestackdigest.app.data.local.ArticleDao
import com.thestackdigest.app.data.local.StackDigestDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): StackDigestDatabase {

        return Room.databaseBuilder(
            context,
            StackDigestDatabase::class.java,
            "stack_digest.db"
        ).build()
    }

    @Provides
    fun provideArticleDao(
        database: StackDigestDatabase
    ): ArticleDao {
        return database.articleDao()
    }
}