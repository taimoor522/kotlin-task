package com.example.switchkotlintask.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.switchkotlintask.data.models.NewsArticle

@Database(entities = [NewsArticle::class], version = 2, exportSchema = false)
abstract class NewsArticleDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsArticleDao

    companion object {
        @Volatile
        private var INSTANCE: NewsArticleDatabase? = null

        fun getDatabase(context: Context): NewsArticleDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewsArticleDatabase::class.java,
                    "news_articles_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}