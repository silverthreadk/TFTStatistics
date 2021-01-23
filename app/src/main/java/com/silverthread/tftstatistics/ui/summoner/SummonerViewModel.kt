package com.silverthread.tftstatistics.ui.summoner

import SingleLiveEvent
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
    private val _summonerLiveData = MutableLiveData<SummonerDTO>()
    val summonerLiveData : LiveData<SummonerDTO>
        get() = _summonerLiveData
    private val _leagueEntryLiveData = MutableLiveData<LeagueEntryDTO>()
    val leagueEntryLiveData : LiveData<LeagueEntryDTO>
        get() = _leagueEntryLiveData
    private val _matchLiveData = MutableLiveData<List<MatchDTO>>()
    val matchLiveData : LiveData<List<MatchDTO>>
        get() = _matchLiveData
    private val _progressLiveData = MutableLiveData<Int>()
    val progressLiveData : LiveData<Int>
        get() = _progressLiveData
    private val _searchEventLiveData = SingleLiveEvent<Void>()
    val searchEventLiveData : LiveData<Void>
        get() = _searchEventLiveData

    fun loadSummoner(summonerName: String) {
        viewModelScope.launch {
            if (isActive) {
                _progressLiveData.value = 0
                val result = remoteApi.getSummoner(summonerName)
                if (result is Success) {
                    _summonerLiveData.value = result.data.body()
                    Log.d("loadSummoner", result.toString())
                    result.data.body()?.id?.let { loadTFTLegueBySummoner(it) }
                    result.data.body()?.puuid?.let { loadMatches(it) }
                    _searchEventLiveData.call()
                } else {
                    Log.d("loadSummoner", result.toString())
                }
                _progressLiveData.value = 8
            }
        }
    }

    fun loadSummonerByPuuid(puuid: String) {
        viewModelScope.launch {
            if (isActive) {
                _progressLiveData.value = 0
                val result = remoteApi.getSummonerByPuuid(puuid)
                if (result is Success) {
                    _summonerLiveData.value = result.data.body()
                    Log.d("loadSummoner", result.toString())
                    result.data.body()?.id?.let { loadTFTLegueBySummoner(it) }
                    result.data.body()?.puuid?.let { loadMatches(it) }
                } else {
                    Log.d("loadSummoner", result.toString())
                }
                _progressLiveData.value = 8
            }
        }
    }

    private suspend fun loadTFTLegueBySummoner(encryptedSummonerId: String) {
        val result = remoteApi.getTFTLegueBySummoner(encryptedSummonerId)
        if (result is Success) {
            if (result.data.body().isNullOrEmpty()) return
            _leagueEntryLiveData.value = result.data.body()?.first()
            Log.d("loadTFTLegueBySummoner", result.toString())
        } else {
            Log.d("loadTFTLegueBySummoner", result.toString())
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

    private suspend fun loadMatches(puuid: String) {
        val result = remoteApi.getMatches(puuid)
        if (result is Success) {
            result.data.body()?.let {
                val matchesList = mutableListOf<MatchDTO>()
                it.forEach { matchId ->
                    (loadMatch(matchId, matchesList))
                }
                _matchLiveData.value = matchesList
            }
            Log.d("loadMatches", result.toString())
        } else {
            Log.d("loadMatches", result.toString())
        }
    }
}