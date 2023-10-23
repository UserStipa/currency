package com.userstipa.currency.ui.search_currency

import com.userstipa.currency.domain.model.Currency

data class SearchUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val list: List<Currency> = emptyList()
)