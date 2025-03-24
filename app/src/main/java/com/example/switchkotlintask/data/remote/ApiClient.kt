package com.example.switchkotlintask.data.remote

import com.example.switchkotlintask.core.constants.AppStrings
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object ApiClient {
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(AppStrings.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
