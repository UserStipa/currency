package com.userstipa.currency.data.api

data class GetCurrenciesDto(
    val data: List<CurrencyDto>,
    val timestamp: String
)
