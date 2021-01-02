package com.silverthread.tftstatistics.networking

import com.silverthread.tftstatistics.model.Failure
import com.silverthread.tftstatistics.model.Success

const val BASE_URL1 = "https://kr.api.riotgames.com"
const val BASE_URL2 = "https://asia.api.riotgames.com"

const val API_KEY = "RGAPI-09c14e86-98e7-4c6e-baeb-41318a8b589b"

class RemoteApi(private val summonerApiService: RemoteApiService, private val matchApiService: RemoteApiService) {
    suspend fun getSummoner(summonerName: String) = try {
        val data = summonerApiService.getSummoner(summonerName, API_KEY)

        if(data.isSuccessful){
            Success(data)
        } else {
            Failure(null)
        }

    } catch (error: Throwable) {
        Failure(error)
    }

    suspend fun getTFTLegueBySummoner(encryptedSummonerId: String) = try {
        val data = summonerApiService.getTFTLegueBySummoner(encryptedSummonerId, API_KEY)

        if(data.isSuccessful){
            Success(data)
        } else {
            Failure(null)
        }

    } catch (error: Throwable) {
        Failure(error)
    }

    suspend fun getMatches(puuid: String) = try {
        val data = matchApiService.getMatches(puuid, "20", API_KEY)

        if(data.isSuccessful){
            Success(data)
        } else {
            Failure(null)
        }

    } catch (error: Throwable) {
        Failure(error)
    }

    suspend fun getMatch(matchId: String) = try {
        val data = matchApiService.getMatch(matchId, API_KEY)

        if(data.isSuccessful){
            Success(data)
        } else {
            Failure(null)
        }

    } catch (error: Throwable) {
        Failure(error)
    }
}