package com.example.switchkotlintask.viewmodel

import com.example.switchkotlintask.core.helpers.NetworkHandler
import com.example.switchkotlintask.data.models.NewsArticle
import com.example.switchkotlintask.data.repository.NewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class NewsViewModel(private val repository: NewsRepository) {

    private val _newsState = MutableStateFlow<NewsState>(NewsState.Error(""))
    val newsState: StateFlow<NewsState> = _newsState

    val _cachedArticles = mutableListOf<NewsArticle>()

    fun fetchTopHeadlines() {
        if(!NetworkHandler.isNetworkConnected) {
            _newsState.value = NewsState.Error("No internet connection\nPlease connect to the internet and try again")
            return
        }
        if (_newsState.value is NewsState.Loading) return
        _newsState.value = NewsState.Loading
        CoroutineScope(Dispatchers.IO).launch {
            repository.getTopHeadlines() { result ->
                result.onSuccess { response ->
                    _cachedArticles.clear()
                    _cachedArticles.addAll(response.articles)
                    _newsState.value = NewsState.Success(response.articles)
                }.onFailure { error ->
                    _newsState.value = NewsState.Error(error.message ?: "Unknown error")
                }
            }
        }
    }

    fun filterArticles(query: String) {
        if (query.isEmpty()) {
            _newsState.value = NewsState.Success(_cachedArticles)
        } else {
            val filteredArticles = _cachedArticles.filter { article ->
                article.title.contains(query, ignoreCase = true) || article.description?.contains(query, ignoreCase = true) ?: false
            }
            _newsState.value = NewsState.Success(filteredArticles)
        }
    }
}


sealed class NewsState {
    data object Loading : NewsState()
    data class Success(val articles: List<NewsArticle>) : NewsState()
    data class Error(val message: String) : NewsState()
}