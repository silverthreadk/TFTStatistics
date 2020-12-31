package com.silverthread.tftstatistics.networking

import com.silverthread.tftstatistics.model.response.Summoner
import retrofit2.Response
import retrofit2.http.*

interface RemoteApiService {

    @GET("/tft/summoner/v1/summoners/by-name/{summonerName}")
    suspend fun getSummoner(@Path("summonerName") summonerName: String, @Query("api_key") apiKey: String): Response<Summoner>

}
