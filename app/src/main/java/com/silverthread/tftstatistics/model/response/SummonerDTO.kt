package com.silverthread.tftstatistics.model.response

import kotlinx.serialization.Serializable

@Serializable
data class SummonerDTO(
    var accountId: String="",
    var profileIconId: String="",
    var revisionDate: String="",
    var name: String="",
    var puuid: String="",
    var id: String="",
    var summonerLevel: Int = 0
)