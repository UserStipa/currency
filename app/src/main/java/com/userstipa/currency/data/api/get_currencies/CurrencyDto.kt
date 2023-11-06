package com.userstipa.currency.data.api.get_currencies

data class CurrencyDto(
    val changePercent24Hr: Double,
    val id: String,
    val marketCapUsd: String,
    val name: String,
    var priceUsd: Double,
    val rank: String,
    val supply: String,
    val symbol: String,
    val volumeUsd24Hr: String,
    val maxSupply: String?,
    val vwap24Hr: String?,
    val explorer: String?,
)