package com.example.switchkotlintask.core.di

import com.example.switchkotlintask.data.remote.ApiClient
import com.example.switchkotlintask.data.remote.NewsApiService
import com.example.switchkotlintask.data.repository.NewsRepository
import com.example.switchkotlintask.viewmodel.NewsViewModel
import org.koin.dsl.module

val appModule = module {
//    single { ApiClient.retrofit.create(NewsApiService::class.java) }
//    single { NewsRepository(get()) }
//    single { NewsViewModel(get()) }

}