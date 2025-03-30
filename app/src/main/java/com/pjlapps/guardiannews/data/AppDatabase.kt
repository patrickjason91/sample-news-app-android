package com.pjlapps.guardiannews.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [
        NewsDetail::class
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
    companion object {
        const val DATABASE_NAME = "guardian_news_database"
    }
}