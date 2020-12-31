package com.silverthread.tftstatistics

import android.app.Application
import android.content.Context
import com.silverthread.tftstatistics.networking.RemoteApi
import com.silverthread.tftstatistics.networking.buildApiService

class App : Application() {

    companion object {
        private lateinit var instance: App

        private val apiService by lazy { buildApiService() }

        val remoteApi by lazy { RemoteApi(apiService) }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}