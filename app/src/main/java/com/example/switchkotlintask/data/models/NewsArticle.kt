package com.example.switchkotlintask.data.models
import com.google.gson.annotations.SerializedName

data class NewsArticle(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String?,
    @SerializedName("urlToImage") val imageUrl: String?,
    @SerializedName("url") val url: String,
    @SerializedName("publishedAt") val publishedAt: String,
    @SerializedName("content") val content: String?,
    @SerializedName("author") val author: String?,
)