package com.userstipa.currency.data.api.get_currency_history

data class PriceTimeDto(
    val circulatingSupply: String,
    val date: String,
    val priceUsd: Double,
    val time: Long
)