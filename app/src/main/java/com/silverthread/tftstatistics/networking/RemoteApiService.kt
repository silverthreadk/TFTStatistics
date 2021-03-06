package com.silverthread.tftstatistics.networking

import com.silverthread.tftstatistics.model.response.LeagueEntryDTO
import com.silverthread.tftstatistics.model.response.MatchDTO
import com.silverthread.tftstatistics.model.response.SummonerDTO
import retrofit2.Response
import retrofit2.http.*

interface RemoteApiService {

    @GET("https://{platform}.api.riotgames.com/tft/summoner/v1/summoners/by-name/{summonerName}")
    suspend fun getSummoner(@Path("platform") platform: String, @Path("summonerName") summonerName: String, @Query("api_key") apiKey: String): Response<SummonerDTO>

    @GET("https://{platform}.api.riotgames.com/tft/summoner/v1/summoners/by-puuid/{puuid}")
    suspend fun getSummonerByPuuid(@Path("platform") platform: String, @Path("puuid") summonerName: String, @Query("api_key") apiKey: String): Response<SummonerDTO>

    @GET("https://{platform}.api.riotgames.com/tft/league/v1/entries/by-summoner/{encryptedSummonerId}")
    suspend fun getTFTLeagueBySummoner(@Path("platform") platform: String, @Path("encryptedSummonerId") summonerName: String, @Query("api_key") apiKey: String): Response<Set<LeagueEntryDTO>>

    @GET("https://{region}.api.riotgames.com/tft/match/v1/matches/by-puuid/{puuid}/ids")
    suspend fun getMatches(@Path("region") region: String, @Path("puuid") puuid: String, @Query("count") count: String, @Query("api_key") apiKey: String): Response<List<String>>

    @GET("https://{region}.api.riotgames.com/tft/match/v1/matches/{matchId}")
    suspend fun getMatch(@Path("region") region: String, @Path("matchId") matchId: String, @Query("api_key") apiKey: String): Response<MatchDTO>
}
