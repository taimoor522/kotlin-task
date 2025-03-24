package com.example.switchkotlintask

import android.app.Application
import android.content.IntentFilter
import android.net.ConnectivityManager.CONNECTIVITY_ACTION
import com.example.switchkotlintask.core.di.appModule
import com.example.switchkotlintask.core.helpers.NetworkHandler
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NewsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NewsApplication)
            modules(appModule)
        }

        val filter = IntentFilter(CONNECTIVITY_ACTION)
        registerReceiver(NetworkHandler(), filter)
    }
}