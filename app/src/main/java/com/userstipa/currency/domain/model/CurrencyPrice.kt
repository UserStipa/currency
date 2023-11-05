package com.userstipa.currency.domain.model

data class CurrencyPrice(
    val id: String,
    val name: String,
    val symbol: String,
    val priceUsd: String,
    val changePercent24Hr: String,
    val isPositiveChangePercent24Hr: Boolean,
    var isEnableCheckbox: Boolean
)