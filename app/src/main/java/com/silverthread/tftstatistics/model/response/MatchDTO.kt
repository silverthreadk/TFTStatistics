package com.silverthread.tftstatistics.model.response

import kotlinx.serialization.Serializable

@Serializable
data class MatchDTO(
    val metadata: MatchDataDTO = MatchDataDTO(),
    val info: InfoDTO = InfoDTO()
)

@Serializable
data class MatchDataDTO(
    val data_version: String="",
    val match_id: String="",
    val participants: List<String> = emptyList()
)

@Serializable
data class InfoDTO(
    val game_datetime: String = "",
    val game_length: String = "",
    val game_variation: String = "",
    val game_version: String = "",
    val participants: List<ParticipantDTO> = emptyList(),
    val queue_id: String = "",
    val tft_set_number: String = ""
)

@Serializable
data class ParticipantDTO(
    val companion: CompanionDTO = CompanionDTO(),
    val gold_left: String = "",
    val last_round: String = "",
    val level: String = "",
    val placement: Int = 0,
    val players_eliminated: String = "",
    val puuid: String = "",
    val time_eliminated: String = "",
    val total_damage_to_players: String = "",
    val traits: List<TraitDTO> = emptyList(),
    val units: List<UnitDTO> = emptyList()
)

@Serializable
data class CompanionDTO(
    var content_ID: String = "",
    var skin_ID: String = "",
    var species: String = "",
)

@Serializable
data class TraitDTO(
    var name: String = "",
    var num_units: Int = 0,
    var style: Int = 0,
    var tier_current: Int = 0,
    var tier_total: String = ""
)

@Serializable
data class UnitDTO(
    var items: List<String> = emptyList(),
    var character_id: String = "",
    var chosen: String = "",
    var name: String = "",
    var rarity: String = "",
    var tier: String = "",
)