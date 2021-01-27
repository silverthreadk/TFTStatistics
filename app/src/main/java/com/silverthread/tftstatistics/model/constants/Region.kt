package com.silverthread.tftstatistics.model.constants

enum class Region(val id:String, val platformRoutingValue:String, val RegionalRoutingValue:String) {
    BR("BR", "br1", "americas"),
    EUNE("EUNE", "eun1", "europe"),
    EUW("EUW", "euw1", "europe"),
    JP("JP", "jp1", "asia"),
    KR("KR", "kr", "asia"),
    LAN("LAN", "la1", "americas"),
    LAS("LAS", "la2", "americas"),
    NA("NA", "na1", "americas"),
    OCE("OCE", "oc1", "americas"),
    TR("TR", "tr1", "europe"),
    RU("RU", "ru", "europe");

    companion object {
        fun fromId(value: String) = Region.values().first { it.id == value }
    }
}