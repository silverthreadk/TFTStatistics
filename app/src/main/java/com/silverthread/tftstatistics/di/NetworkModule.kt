package com.silverthread.tftstatistics.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.silverthread.tftstatistics.networking.RemoteApi
import com.silverthread.tftstatistics.networking.RemoteApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideApiService(): RemoteApiService {
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .connectTimeout(1, TimeUnit.MINUTES)
                .build()
        return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://ddragon.leagueoflegends.com/")
                .addConverterFactory(
                        Json {
                            isLenient = true
                            ignoreUnknownKeys = true
                        }.asConverterFactory("application/json".toMediaType())
                )
                .build().create(RemoteApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteApi(remoteApiService: RemoteApiService): RemoteApi {
        return RemoteApi(remoteApiService)
    }
}