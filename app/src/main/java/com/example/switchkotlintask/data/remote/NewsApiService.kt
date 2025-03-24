package com.example.switchkotlintask.data.remote


import com.example.switchkotlintask.data.models.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call

interface NewsApiService {
    @GET("top-headlines")
    fun getTopHeadlines(
        @Query("country") country: String = "us",
        @Query("apiKey") apiKey: String
    ): Call<NewsResponse>
}