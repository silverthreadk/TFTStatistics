package com.silverthread.tftstatistics.model.response

import kotlinx.serialization.Serializable

@Serializable
data class LeagueEntryDTO(
    val leagueId: String = "",
    val queueType: String = "",
    val tier: String = "Unranked",
    val rank: String = "",
    val summonerId: String = "",
    val summonerName: String = "",
    val leaguePoints: Int = 0,
    val wins: Int = 0,
    val losses: Int = 0,
    val veteran: String = "",
    val inactive: String = "",
    val freshBlood: String = "",
    val hotStreak: String = ""
)