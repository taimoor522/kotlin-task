package com.example.switchkotlintask.data.repository

import com.example.switchkotlintask.core.constants.AppStrings
import com.example.switchkotlintask.core.helpers.NetworkHandler
import com.example.switchkotlintask.data.local.NewsArticleDao
import com.example.switchkotlintask.data.models.NewsArticle
import com.example.switchkotlintask.data.models.NewsResponse
import com.example.switchkotlintask.data.remote.NewsApiService
import com.example.switchkotlintask.viewmodel.NewsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Date


class NewsRepository(
    private val apiService: NewsApiService,
    private val newsArticleDao: NewsArticleDao,
) {

    fun getTopHeadlines(onResult: (Result<List<NewsArticle>>) -> Unit) {
        if (NetworkHandler.isNetworkConnected) {
            apiService.getTopHeadlines(apiKey = AppStrings.API_KEY).enqueue(object : Callback<NewsResponse> {
                override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                    if (response.isSuccessful) {
                        response.body()?.let { newsResponse ->
                            val articles = newsResponse.articles.map { article ->
                                NewsArticle(
                                    title = article.title,
                                    description = article.description,
                                    url = article.url,
                                    imageUrl = article.imageUrl,
                                    publishedAt = article.publishedAt,
                                    content = article.content,
                                    author = article.author,
                                )
                            }

                            CoroutineScope(Dispatchers.IO).launch {
                                newsArticleDao.clearArticles()
                                newsArticleDao.insertArticles(articles)
                            }

                            onResult(Result.success(articles))
                        } ?: onResult(Result.failure(Exception("Empty response")))
                    } else {
                        onResult(Result.failure(Exception("Error: ${response.message()}")))
                    }
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    fetchFromDatabase(onResult, t)
                }
            })
        } else {
            fetchFromDatabase(onResult, Exception("No internet connection"))
        }
    }

    private fun fetchFromDatabase(onResult: (Result<List<NewsArticle>>) -> Unit, error: Throwable) {
        CoroutineScope(Dispatchers.IO).launch {
            val cachedArticles = newsArticleDao.getArticles()
            if (cachedArticles.isNotEmpty()) {
                onResult(Result.success(cachedArticles))
            } else {
                onResult(Result.failure(error))
            }
        }
    }
}
