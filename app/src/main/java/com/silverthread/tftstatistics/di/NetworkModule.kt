package com.silverthread.tftstatistics.di

import com.silverthread.tftstatistics.networking.RemoteApi
import com.silverthread.tftstatistics.networking.RemoteApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideApiService(): RemoteApiService {
        val OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .connectTimeout(1, TimeUnit.MINUTES)
                .build()
        return Retrofit.Builder()
            .client(OkHttpClient)
            .baseUrl("https://ddragon.leagueoflegends.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RemoteApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteApi(remoteApiService: RemoteApiService): RemoteApi {
        return RemoteApi(remoteApiService)
    }
}