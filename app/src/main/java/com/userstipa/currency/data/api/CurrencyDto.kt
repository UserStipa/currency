package com.userstipa.currency.data.api

data class CurrencyDto(
    val changePercent24Hr: String,
    val explorer: String,
    val id: String,
    val marketCapUsd: String,
    val maxSupply: String,
    val name: String,
    var priceUsd: Double,
    val rank: String,
    val supply: String,
    val symbol: String,
    val volumeUsd24Hr: String,
    val vwap24Hr: String,
)