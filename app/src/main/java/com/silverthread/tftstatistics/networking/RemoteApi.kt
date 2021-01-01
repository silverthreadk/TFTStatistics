package com.silverthread.tftstatistics.networking

import com.silverthread.tftstatistics.model.Failure
import com.silverthread.tftstatistics.model.Success

const val BASE_URL = "https://kr.api.riotgames.com"
const val API_KEY = "RGAPI-09c14e86-98e7-4c6e-baeb-41318a8b589b"

class RemoteApi(private val apiService: RemoteApiService) {
    suspend fun getSummoner(summonerName: String) = try {
        val data = apiService.getSummoner(summonerName, API_KEY)

        if(data.isSuccessful){
            Success(data)
        } else {
            Failure(null)
        }

    } catch (error: Throwable) {
        Failure(error)
    }

    suspend fun getTFTLegueBySummoner(encryptedSummonerId: String) = try {
        val data = apiService.getTFTLegueBySummoner(encryptedSummonerId, API_KEY)

        if(data.isSuccessful){
            Success(data)
        } else {
            Failure(null)
        }

    } catch (error: Throwable) {
        Failure(error)
    }
}