package com.example.switchkotlintask.data.models
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "news_articles")
data class NewsArticle(
    @PrimaryKey @ColumnInfo(name = "url")
    val url: String,

    @SerializedName("title")
    @ColumnInfo(name = "title")
    val title: String,

    @SerializedName("description")
    @ColumnInfo(name = "description")
    val description: String?,

    @SerializedName("urlToImage")
    @ColumnInfo(name = "image_url")
    val imageUrl: String?,

    @SerializedName("publishedAt")
    @ColumnInfo(name = "published_at")
    val publishedAt: String,

    @SerializedName("content")
    @ColumnInfo(name = "content")
    val content: String?,

    @SerializedName("author")
    @ColumnInfo(name = "author")
    val author: String?
)