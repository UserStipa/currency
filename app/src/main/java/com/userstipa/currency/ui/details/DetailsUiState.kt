package com.userstipa.currency.ui.details

import com.userstipa.currency.domain.model.Currency

data class DetailsUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val currency: Currency? = null
)