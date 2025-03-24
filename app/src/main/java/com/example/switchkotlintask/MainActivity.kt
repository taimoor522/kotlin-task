package com.example.switchkotlintask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.switchkotlintask.data.remote.ApiClient
import com.example.switchkotlintask.data.remote.NewsApiService
import com.example.switchkotlintask.data.repository.NewsRepository
import com.example.switchkotlintask.ui.theme.NewsApiAssignmentTheme
import com.example.switchkotlintask.view.NewsListScreen
import com.example.switchkotlintask.viewmodel.NewsViewModel

class MainActivity : ComponentActivity() {

    private lateinit var repository: NewsRepository
    private lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // I wanted to use Koin for Dependency Injection but due to limited time
        // and change in Koin's API, I decided to use manual DI
        // I tried to implement Koin but it was giving me errors

        repository = NewsRepository(ApiClient.retrofit.create(NewsApiService::class.java))
        viewModel = NewsViewModel(repository)

        setContent {
            NewsApiAssignmentTheme {
                NewsListScreen(viewModel = viewModel )
            }
        }
    }
}
