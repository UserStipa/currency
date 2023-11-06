package com.userstipa.currency.domain.model

data class PriceTime(
    val priceUsd: Double,
    val priceUsdFormatted: String,
    val dateTime: String
)
