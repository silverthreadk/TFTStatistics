package com.silverthread.tftstatistics.networking

import com.silverthread.tftstatistics.model.response.LeagueEntryDTO
import com.silverthread.tftstatistics.model.response.MatchDTO
import com.silverthread.tftstatistics.model.response.SummonerDTO
import retrofit2.Response
import retrofit2.http.*

interface RemoteApiService {

    @GET("/tft/summoner/v1/summoners/by-name/{summonerName}")
    suspend fun getSummoner(@Path("summonerName") summonerName: String, @Query("api_key") apiKey: String): Response<SummonerDTO>

    @GET("/tft/league/v1/entries/by-summoner/{encryptedSummonerId}")
    suspend fun getTFTLegueBySummoner(@Path("encryptedSummonerId") summonerName: String, @Query("api_key") apiKey: String): Response<Set<LeagueEntryDTO>>

    @GET("/tft/match/v1/matches/by-puuid/{puuid}/ids")
    suspend fun getMatches(@Path("puuid") puuid: String, @Query("count") count: String, @Query("api_key") apiKey: String): Response<List<String>>

    @GET("/tft/match/v1/matches/{matchId}")
    suspend fun getMatch(@Path("matchId") matchId: String, @Query("api_key") apiKey: String): Response<MatchDTO>
}
