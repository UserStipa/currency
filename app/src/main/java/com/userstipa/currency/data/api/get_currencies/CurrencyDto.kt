package com.userstipa.currency.data.api.get_currencies

data class CurrencyDto(
    val changePercent24Hr: Double,
    val id: String,
    val marketCapUsd: Double,
    val name: String,
    var priceUsd: Double,
    val rank: String,
    val supply: Double,
    val symbol: String,
    val volumeUsd24Hr: String,
    val maxSupply: Double?,
    val vwap24Hr: Double?,
    val explorer: String?,
)