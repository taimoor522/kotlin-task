package com.example.switchkotlintask

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.example.switchkotlintask.data.local.NewsArticleDao
import com.example.switchkotlintask.data.local.NewsArticleDatabase
import com.example.switchkotlintask.data.remote.ApiClient
import com.example.switchkotlintask.data.remote.NewsApiService
import com.example.switchkotlintask.data.repository.NewsRepository
import com.example.switchkotlintask.ui.theme.NewsApiAssignmentTheme
import com.example.switchkotlintask.view.NewsListScreen
import com.example.switchkotlintask.viewmodel.NewsViewModel

class MainActivity : ComponentActivity() {

    // I wanted to use Koin for Dependency Injection but due to limited time
    // and change in Koin's API, I decided to use manual DI
    // I tried to implement Koin but it was giving me errors

    private lateinit var newsDatabase: NewsArticleDatabase
    private lateinit var newsArticleDao: NewsArticleDao
    private lateinit var repository: NewsRepository
    private lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       try {
           newsDatabase = NewsArticleDatabase.getDatabase(this)
       } catch (e: Exception) {
           Log.d(e.toString(), "")
       }

        newsArticleDao = newsDatabase.newsDao()
        repository = NewsRepository(ApiClient.retrofit.create(NewsApiService::class.java), newsArticleDao)
        viewModel = NewsViewModel(repository)

        setContent {
            NewsApiAssignmentTheme {
                NewsListScreen(viewModel = viewModel )
            }
        }
    }
}
