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
    val MatchLiveData = MutableLiveData<List<MatchDTO>>()
    val progressData = MutableLiveData<Int>()

    fun loadSummoner(summonerName: String) {
        viewModelScope.launch {
            if (isActive) {
                progressData.postValue(0)
                val result = remoteApi.getSummoner(summonerName)
                if (result is Success) {
                    summonerLiveData.postValue(result.data.body())
                    Log.d("loadSummoner", result.toString())
                    result.data.body()?.id?.let { loadTFTLegueBySummoner(it) }
                    result.data.body()?.puuid?.let { loadMatches(it) }
                } else {
                    Log.d("loadSummoner", result.toString())
                }
                progressData.postValue(8)
            }
        }
    }

    suspend fun loadTFTLegueBySummoner(encryptedSummonerId: String) {
        val result = remoteApi.getTFTLegueBySummoner(encryptedSummonerId)
        if (result is Success) {
            if (result.data.body().isNullOrEmpty()) return
            leagueEntryLiveData.postValue(result.data.body()?.first())
            Log.d("loadTFTLegueBySummoner", result.toString())
        } else {
            Log.d("loadTFTLegueBySummoner", result.toString())
        }
    }

    suspend fun loadMatches(puuid: String) {
        val result = remoteApi.getMatches(puuid)
        if (result is Success) {
            result.data.body()?.let {
                val matchesList = mutableListOf<MatchDTO>()
                it.forEach { matchId ->
                    (loadMatch(matchId, matchesList))
                }
                MatchLiveData.postValue(matchesList)
            }
            Log.d("loadMatches", result.toString())
        } else {
            Log.d("loadMatches", result.toString())
        }
    }

    suspend fun loadMatch(matchId: String, matchesList: MutableList<MatchDTO>) {
        val result = remoteApi.getMatch(matchId)
        if (result is Success) {
            result.data.body()?.let {
                matchesList.add(it)
            }
            Log.d("loadMatch", result.toString())
        } else {
            Log.d("loadMatch", result.toString())
        }
    }
}