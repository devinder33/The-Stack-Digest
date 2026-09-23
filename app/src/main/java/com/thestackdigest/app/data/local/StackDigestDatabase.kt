package com.thestackdigest.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        ArticleEntity::class
    ],
    version = 1,
    exportSchema = false,
)

@TypeConverters(
    ArticleConverters::class
)
abstract class StackDigestDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao
}