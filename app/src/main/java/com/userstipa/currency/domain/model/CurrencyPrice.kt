package com.userstipa.currency.domain.model

data class CurrencyPrice(
    val id: String,
    val name: String,
    val symbol: String,
    val priceUsd: String,
    var isEnableCheckbox: Boolean
)