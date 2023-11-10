package com.userstipa.currency.ui.details

import com.userstipa.currency.domain.model.CurrencyPriceDetails

data class DetailsUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val currency: CurrencyPriceDetails? = null
)