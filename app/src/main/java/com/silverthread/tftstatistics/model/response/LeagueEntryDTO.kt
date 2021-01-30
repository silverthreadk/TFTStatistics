package com.silverthread.tftstatistics.model.response

class LeagueEntryDTO {
    var leagueId: String? = null
    var queueType: String? = null
    var tier: String? = "unranked"
    var rank: String? = null
    var summonerId: String? = null
    var summonerName: String? = null
    var leaguePoints: Int = 0
    var wins: Int = 0
    var losses: Int = 0
    var veteran: String? = null
    var inactive: String? = null
    var freshBlood: String? = null
    var hotStreak: String? = null
}