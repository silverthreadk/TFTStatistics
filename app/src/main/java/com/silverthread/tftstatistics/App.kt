package com.silverthread.tftstatistics

import android.app.Application
import android.content.Context
import com.silverthread.tftstatistics.networking.BASE_URL1
import com.silverthread.tftstatistics.networking.BASE_URL2
import com.silverthread.tftstatistics.networking.RemoteApi
import com.silverthread.tftstatistics.networking.buildApiService

class App : Application() {

    companion object {
        private lateinit var instance: App

        private val summonerApiService by lazy { buildApiService(BASE_URL1) }
        private val matchApiService by lazy { buildApiService(BASE_URL2) }

        val remoteApi by lazy { RemoteApi(summonerApiService, matchApiService) }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}