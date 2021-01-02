package com.silverthread.tftstatistics.networking

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun buildClient(): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .readTimeout(1,TimeUnit.MINUTES)
        .writeTimeout(2,TimeUnit.MINUTES)
        .connectTimeout(1, TimeUnit.MINUTES)
        .build()

fun buildRetrofit(baseUrl: String): Retrofit {
    return Retrofit.Builder()
        .client(buildClient())
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun buildApiService(baseUrl: String): RemoteApiService =
    buildRetrofit(baseUrl).create(RemoteApiService::class.java)