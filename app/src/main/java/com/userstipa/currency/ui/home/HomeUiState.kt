package com.userstipa.currency.ui.home

import com.userstipa.currency.domain.model.Currency

data class HomeUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val list: List<Currency> = emptyList()
)
