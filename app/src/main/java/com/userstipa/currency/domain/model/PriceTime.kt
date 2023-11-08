package com.userstipa.currency.domain.model

import java.time.ZonedDateTime

data class PriceTime(
    val priceUsd: Double,
    val priceUsdFormatted: String,
    val dateTime: ZonedDateTime,
    val dateTimeFormatted: String
)
