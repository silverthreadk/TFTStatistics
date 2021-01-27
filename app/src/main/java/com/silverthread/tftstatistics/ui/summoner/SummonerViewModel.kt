package com.silverthread.tftstatistics.ui.summoner

import SingleLiveEvent
import android.util.Log
import androidx.lifecycle.*
import com.silverthread.tftstatistics.App
import com.silverthread.tftstatistics.model.Success
import com.silverthread.tftstatistics.model.constants.Region
import com.silverthread.tftstatistics.model.response.LeagueEntryDTO
import com.silverthread.tftstatistics.model.response.MatchDTO
import com.silverthread.tftstatistics.model.response.SummonerDTO
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class SummonerViewModel: ViewModel() {
    private val remoteApi = App.remoteApi
    private val _regionLiveData = MutableLiveData<Region>()
    val regionLiveData : LiveData<Region>
        get() = _regionLiveData
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

    init {
        setRegion(Region.KR)
    }

    fun setRegion(region: Region){
        _regionLiveData.value = region
    }

    fun loadSummoner(summonerName: String) {
        viewModelScope.launch {
            if (isActive) {
                _progressLiveData.value = 0
                val result = remoteApi.getSummoner((regionLiveData.value?: Region.KR).platformRoutingValue, summonerName)
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
                val result = remoteApi.getSummonerByPuuid((regionLiveData.value?: Region.KR).platformRoutingValue, puuid)
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
        val result = remoteApi.getTFTLegueBySummoner((regionLiveData.value?: Region.KR).platformRoutingValue, encryptedSummonerId)
        if (result is Success) {
            if (result.data.body().isNullOrEmpty()) return
            _leagueEntryLiveData.value = result.data.body()?.first()
            Log.d("loadTFTLegueBySummoner", result.toString())
        } else {
            Log.d("loadTFTLegueBySummoner", result.toString())
        }
    }

    suspend fun loadMatch(matchId: String, matchesList: MutableList<MatchDTO>) {
        val result = remoteApi.getMatch((regionLiveData.value?: Region.KR).RegionalRoutingValue, matchId)
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
        val result = remoteApi.getMatches((regionLiveData.value?: Region.KR).RegionalRoutingValue, puuid)
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