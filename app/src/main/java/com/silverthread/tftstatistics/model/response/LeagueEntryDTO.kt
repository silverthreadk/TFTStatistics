package com.silverthread.tftstatistics.model.response

import kotlinx.serialization.Serializable

@Serializable
data class LeagueEntryDTO(
    var leagueId: String="",
    var queueType: String="",
    var tier: String = "Unranked",
    var rank: String = "",
    var summonerId: String="",
    var summonerName: String="",
    var leaguePoints: Int = 0,
    var wins: Int = 0,
    var losses: Int = 0,
    var veteran: String="",
    var inactive: String?="",
    var freshBlood: String="",
    var hotStreak: String=""
)