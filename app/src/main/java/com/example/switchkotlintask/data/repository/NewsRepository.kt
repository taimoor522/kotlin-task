package com.example.switchkotlintask.data.repository

import com.example.switchkotlintask.core.constants.AppStrings
import com.example.switchkotlintask.data.models.NewsResponse
import com.example.switchkotlintask.data.remote.NewsApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewsRepository(private val apiService: NewsApiService)  {

    fun getTopHeadlines(onResult: (Result<NewsResponse>) -> Unit) {
        apiService.getTopHeadlines(apiKey = AppStrings.API_KEY).enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { onResult(Result.success(it)) }
                        ?: onResult(Result.failure(Exception("Empty response")))
                } else {
                    onResult(Result.failure(Exception("Error: ${response.message()}")))
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                onResult(Result.failure(t))
            }
        })
    }
}