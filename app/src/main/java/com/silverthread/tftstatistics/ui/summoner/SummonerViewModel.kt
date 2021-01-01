package com.silverthread.tftstatistics.ui.summoner

import android.util.Log
import androidx.lifecycle.*
import com.silverthread.tftstatistics.App
import com.silverthread.tftstatistics.model.Success
import com.silverthread.tftstatistics.model.response.LeagueEntry
import com.silverthread.tftstatistics.model.response.Summoner
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class SummonerViewModel: ViewModel() {
    private val remoteApi = App.remoteApi

    val summonerLiveData = MutableLiveData<Summoner>()
    val leagueEntryLiveData = MutableLiveData<LeagueEntry>()

    fun getSummoner(summonerName: String) {
        viewModelScope.launch {
            if (isActive) {
                val result = remoteApi.getSummoner(summonerName)
                if (result is Success) {
                    summonerLiveData.postValue(result.data.body())
                    Log.d("getSummoner", result.toString())
                    result.data.body()?.id?.let { getTFTLegueBySummoner(it) }
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
}