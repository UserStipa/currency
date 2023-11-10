package com.userstipa.currency.data.api.get_currency_history

data class WrapperPriceTimeDto(
    val `data`: List<PriceTimeDto>,
    val timestamp: Long
)