package com.silverthread.tftstatistics.model.response

import kotlinx.serialization.Serializable

@Serializable
data class MatchDTO(
    var metadata: MatchDataDTO? = null,
    var info: InfoDTO? = null
)

@Serializable
data class MatchDataDTO(
    var data_version: String="",
    var match_id: String="",
    var participants: List<String>? = null
)

@Serializable
data class InfoDTO(
    var game_datetime: String="",
    var game_length: String="",
    var game_variation: String="",
    var game_version: String="",
    var participants: List<ParticipantDTO>? = null,
    var queue_id: String="",
    var tft_set_number: String=""
)

@Serializable
data class ParticipantDTO(
    var companion: CompanionDTO? = null,
    var gold_left: String="",
    var last_round: String="",
    var level: String="",
    var placement: Int = 0,
    var players_eliminated: String="",
    var puuid: String? = null,
    var time_eliminated: String="",
    var total_damage_to_players: String="",
    var traits: List<TraitDTO>? = null,
    var units: List<UnitDTO>? = null
)

@Serializable
data class CompanionDTO(
    var content_ID: String="",
    var skin_ID: String="",
    var species: String="",
)

@Serializable
data class TraitDTO(
    var name: String="",
    var num_units: Int = 0,
    var style: Int = 0,
    var tier_current: Int = 0,
    var tier_total: String=""
)

@Serializable
data class UnitDTO(
    var items: List<String>? = null,
    var character_id: String="",
    var chosen: String="",
    var name: String="",
    var rarity: String="",
    var tier: String="",
)