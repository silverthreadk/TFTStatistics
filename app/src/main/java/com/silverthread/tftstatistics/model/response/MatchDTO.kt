package com.silverthread.tftstatistics.model.response

class MatchDTO {
    var metadata: MatchDataDTO? = null
    var info: InfoDTO? = null
}

class MatchDataDTO {
    var data_version: String? = null
    var match_id: String? = null
    var participants: List<String>? = null
}

class InfoDTO {
    var game_datetime: String? = null
    var game_length: String? = null
    var game_variation: String? = null
    var game_version: String? = null
    var participants: List<ParticipantDTO>? = null
    var queue_id: String? = null
    var tft_set_number: String? = null
}

class ParticipantDTO  {
    var companion: CompanionDTO? = null
    var gold_left: String? = null
    var last_round: String? = null
    var level: String? = null
    var placement: Int = 0
    var players_eliminated: String? = null
    var puuid: String? = null
    var time_eliminated: String? = null
    var total_damage_to_players: String? = null
    var traits: List<TraitDTO>? = null
    var units: List<UnitDTO>? = null
}

class CompanionDTO  {
    var content_ID: String? = null
    var skin_ID: String? = null
    var species: String? = null
}

class TraitDTO  {
    var name: String? = null
    var num_units: Int = 0
    var style: Int = 0
    var tier_current: Int = 0
    var tier_total: String? = null
}

class UnitDTO {
    var items: List<String>? = null
    var character_id: String? = null
    var chosen: String? = null
    var name: String? = null
    var rarity: String? = null
    var tier: String? = null
}