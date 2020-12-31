package com.silverthread.tftstatistics.ui.summoner

import android.util.Log
import androidx.lifecycle.*
import com.silverthread.tftstatistics.App
import com.silverthread.tftstatistics.model.Success
import com.silverthread.tftstatistics.model.response.Summoner
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class SummonerViewModel: ViewModel() {
    private val remoteApi = App.remoteApi

    val summonerLiveData = MutableLiveData<Summoner>()

    fun getSummoner(summonerName: String) {
        viewModelScope.launch {
            if (isActive) {
                val result = remoteApi.getSummoner(summonerName)
                if (result is Success) {
                    summonerLiveData.postValue(result.data.body())
                    Log.d("getSummoner : success", result.toString())
                } else {
                    Log.d("getSummoner : fail", result.toString())
                }
            }
        }
    }
}