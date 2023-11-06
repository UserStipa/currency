package com.userstipa.currency.data.api.get_currencies

data class WrapperCurrenciesDto(
    val data: List<CurrencyDto>,
    val timestamp: String
)
