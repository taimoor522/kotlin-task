package com.example.switchkotlintask.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.switchkotlintask.data.models.NewsArticle

@Dao
interface NewsArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<NewsArticle>)

    @Query("SELECT * FROM news_articles ORDER BY publishedAt DESC")
    suspend fun getArticles(): List<NewsArticle>

    @Query("DELETE FROM news_articles")
    suspend fun clearArticles()
}