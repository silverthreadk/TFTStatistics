package com.silverthread.tftstatistics.ui.summoner

import SingleLiveEvent
import androidx.lifecycle.*
import com.silverthread.tftstatistics.App
import com.silverthread.tftstatistics.model.CompositeItem
import com.silverthread.tftstatistics.model.Header
import com.silverthread.tftstatistics.model.StatData
import com.silverthread.tftstatistics.model.Success
import com.silverthread.tftstatistics.model.constants.Region
import com.silverthread.tftstatistics.model.response.LeagueEntryDTO
import com.silverthread.tftstatistics.model.response.MatchDTO
import com.silverthread.tftstatistics.model.response.SummonerDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
    private val _unitStatLiveData = MutableLiveData<List<CompositeItem>>()
    val unitStatLiveData : LiveData<List<CompositeItem>>
        get() = _unitStatLiveData
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
                    result.data.body()?.id?.let { loadTFTLegueBySummoner(it) }
                    result.data.body()?.puuid?.let { loadMatches(it) }
                    _searchEventLiveData.call()
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
                    result.data.body()?.id?.let { loadTFTLegueBySummoner(it) }
                    result.data.body()?.puuid?.let { loadMatches(it) }
                }
                _progressLiveData.value = 8
            }
        }
    }

    private suspend fun loadTFTLegueBySummoner(encryptedSummonerId: String) {
        val result = remoteApi.getTFTLegueBySummoner((regionLiveData.value?: Region.KR).platformRoutingValue, encryptedSummonerId)
        if (result is Success) {
            if (result.data.body().isNullOrEmpty()) {
                _leagueEntryLiveData.value = LeagueEntryDTO()
                return
            }
            _leagueEntryLiveData.value = result.data.body()?.first()
        } else {
            _leagueEntryLiveData.value = LeagueEntryDTO()
        }
    }

    private suspend fun loadMatch(matchId: String, matchesList: MutableList<MatchDTO>) {
        val result = remoteApi.getMatch((regionLiveData.value?: Region.KR).RegionalRoutingValue, matchId)
        if (result is Success) {
            result.data.body()?.let {
                matchesList.add(it)
            }
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
                loadStat(puuid)
            }
        }
    }

    private suspend fun loadStat(puuid: String) {
        if (matchLiveData.value.isNullOrEmpty()) return
        withContext(Dispatchers.Default){
            val m = mutableMapOf<String, StatData>()
            matchLiveData.value!!.forEach { match ->
                match.info?.participants?.filter { participant ->
                    participant.puuid == puuid
                }?.forEach{ summoner ->
                    val isWin = summoner.placement == 1
                    val isTop4 = summoner.placement >= 4
                    summoner.units?.forEach { unit->
                        val s = m.getOrDefault(unit.character_id, StatData())
                        s.rarity = unit.rarity.toString()
                        s.games += 1
                        s.name = unit.character_id.toString()
                        s.place += summoner.placement
                        if (isWin) s.wins += 1
                        if (isTop4) s.top4 += 1
                        m.put(unit.character_id!!, s)
                    }
                }
            }
            val list = mutableListOf<CompositeItem>()
            list.add(CompositeItem.withHeader(Header()))
            list.addAll(m.values.toList().sortedByDescending { it.games }.map{CompositeItem.withStatData(it)})
            _unitStatLiveData.postValue(list)
        }
    }
}