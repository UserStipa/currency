package com.userstipa.currency.ui.details

import com.userstipa.currency.domain.model.CurrencyPriceDetails
import com.userstipa.currency.domain.model.HistoryRange

data class DetailsUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val currency: CurrencyPriceDetails? = null,
    val historyRange: HistoryRange = HistoryRange.DEFAULT_VALUE
)