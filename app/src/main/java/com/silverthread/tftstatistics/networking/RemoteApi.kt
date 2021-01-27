package com.silverthread.tftstatistics.networking

import com.silverthread.tftstatistics.BuildConfig
import com.silverthread.tftstatistics.model.Failure
import com.silverthread.tftstatistics.model.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

const val BASE_URL1 = "https://kr.api.riotgames.com"
const val BASE_URL2 = "https://asia.api.riotgames.com"

const val API_KEY = BuildConfig.ApiKey

class RemoteApi(private val apiService: RemoteApiService) {
    suspend fun getSummoner(platform: String, summonerName: String) = try {
        val data = apiService.getSummoner(platform, summonerName, API_KEY)

        if(data.isSuccessful){
            Success(data)
        } else {
            Failure(null)
        }

    } catch (error: Throwable) {
        Failure(error)
    }

    suspend fun getSummonerByPuuid(platform: String, puuid: String) = try {
        val data = apiService.getSummonerByPuuid(platform, puuid, API_KEY)

        if(data.isSuccessful){
            Success(data)
        } else {
            Failure(null)
        }

    } catch (error: Throwable) {
        Failure(error)
    }

    suspend fun getTFTLegueBySummoner(platform: String, encryptedSummonerId: String) = try {
        val data = apiService.getTFTLegueBySummoner(platform, encryptedSummonerId, API_KEY)

        if(data.isSuccessful){
            Success(data)
        } else {
            Failure(null)
        }

    } catch (error: Throwable) {
        Failure(error)
    }

    suspend fun getMatches(region: String, puuid: String) = try {
        val data = apiService.getMatches(region, puuid, "20", API_KEY)

        if(data.isSuccessful){
            Success(data)
        } else {
            Failure(null)
        }

    } catch (error: Throwable) {
        Failure(error)
    }

    suspend fun getMatch(region: String, matchId: String) = try {
        val data = apiService.getMatch(region, matchId, API_KEY)

        if(data.isSuccessful){
            Success(data)
        } else {
            Failure(null)
        }

    } catch (error: Throwable) {
        Failure(error)
    }
}