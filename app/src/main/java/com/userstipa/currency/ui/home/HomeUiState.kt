package com.userstipa.currency.ui.home

import com.userstipa.currency.domain.model.CurrencyPrice

data class HomeUiState(
    val isLoading: Boolean = false,
    val isLoadingComplete: Boolean = false,
    val error: String? = null,
    val list: List<CurrencyPrice> = emptyList()
)
