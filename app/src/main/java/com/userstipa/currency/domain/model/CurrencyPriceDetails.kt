package com.userstipa.currency.domain.model

data class CurrencyPriceDetails(
    val id: String,
    val name: String,
    val symbol: String,
    var priceUsd: Double,
    val changePercent24Hr: Double,
    val isPositiveChangePercent24Hr: Boolean,
    val marketCapUsd: String,
    val maxSupply: String,
    val rank: String,
    val supply: String,
    val volumeUsd24Hr: String,
    val vwap24Hr: String,
    val explorer: String,
)