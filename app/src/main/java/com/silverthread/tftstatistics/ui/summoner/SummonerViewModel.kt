package com.silverthread.tftstatistics.ui.summoner

import android.util.Log
import androidx.lifecycle.*
import com.silverthread.tftstatistics.App
import com.silverthread.tftstatistics.model.Success
import com.silverthread.tftstatistics.model.response.LeagueEntryDTO
import com.silverthread.tftstatistics.model.response.MatchDTO
import com.silverthread.tftstatistics.model.response.SummonerDTO
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class SummonerViewModel: ViewModel() {
    private val remoteApi = App.remoteApi

    val summonerLiveData = MutableLiveData<SummonerDTO>()
    val leagueEntryLiveData = MutableLiveData<LeagueEntryDTO>()
    val MatchLiveData = MutableLiveData<MatchDTO>()

    fun getSummoner(summonerName: String) {
        viewModelScope.launch {
            if (isActive) {
                val result = remoteApi.getSummoner(summonerName)
                if (result is Success) {
                    summonerLiveData.postValue(result.data.body())
                    Log.d("getSummoner", result.toString())
                    result.data.body()?.id?.let { getTFTLegueBySummoner(it) }
                    result.data.body()?.puuid?.let { getMatches(it) }
                } else {
                    Log.d("getSummoner", result.toString())
                }
            }
        }
    }

    suspend fun getTFTLegueBySummoner(encryptedSummonerId: String) {
        val result = remoteApi.getTFTLegueBySummoner(encryptedSummonerId)
        if (result is Success) {
            if (result.data.body().isNullOrEmpty()) return
            leagueEntryLiveData.postValue(result.data.body()?.first())
            Log.d("getTFTLegueBySummoner", result.toString())
        } else {
            Log.d("getTFTLegueBySummoner", result.toString())
        }
    }

    suspend fun getMatches(puuid: String) {
        val result = remoteApi.getMatches(puuid)
        if (result is Success) {
            result.data.body()?.let {
                it.forEach { matchId ->
                    getMatch(matchId)
                }
            }
            Log.d("getMatches", result.toString())
        } else {
            Log.d("getMatches", result.toString())
        }
    }

    suspend fun getMatch(matchId: String) {
        val result = remoteApi.getMatch(matchId)
        if (result is Success) {
            result.data.body()?.let {
                MatchLiveData.postValue(result.data.body())
            }
            Log.d("getMatch", result.toString())
        } else {
            Log.d("getMatch", result.toString())
        }
    }
}