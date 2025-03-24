package com.example.switchkotlintask.core.helpers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

class NetworkHandler : BroadcastReceiver() {

    companion object {
        var isNetworkConnected = true
    }

    override fun onReceive(context: Context, intent: Intent) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        isNetworkConnected = networkInfo != null && networkInfo.isConnected
    }
}