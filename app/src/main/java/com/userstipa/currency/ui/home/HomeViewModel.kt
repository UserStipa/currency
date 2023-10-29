package com.userstipa.currency.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.userstipa.currency.di.dispatchers.DispatcherProvider
import com.userstipa.currency.domain.Resource
import com.userstipa.currency.domain.model.CurrencyPrice
import com.userstipa.currency.domain.model.CurrencyPriceDetail
import com.userstipa.currency.domain.usecases.get_my_currencies.GetMyCurrencies
import com.userstipa.currency.domain.usecases.new_currencies_prices.NewCurrenciesPrices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getMyCurrencies: GetMyCurrencies,
    private val newCurrenciesPrices: NewCurrenciesPrices,
    private val dispatcher: DispatcherProvider
) : ViewModel() {

    private val webSocketScope = CoroutineScope(Job())
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun fetchData() {
        viewModelScope.launch(dispatcher.io) {
            getMyCurrencies.launch().collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = result.exception.message
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        _uiState.update { it.copy(isLoading = false, list = result.data) }
                    }
                }
            }
        }
    }

    fun subscribeNewPrices() {
        webSocketScope.launch(dispatcher.io) {
            newCurrenciesPrices.subscribe().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _uiState.update { it.copy(list = updateListByNewPrices(result.data)) }
                    }

                    is Resource.Error -> {
                        _uiState.update { it.copy(error = result.exception.message) }
                        unsubscribeNewPrices()
                    }

                    else -> {}
                }
            }
        }
    }

    fun unsubscribeNewPrices() {
        webSocketScope.coroutineContext.cancelChildren()
    }

    private fun updateListByNewPrices(newPrices: List<CurrencyPrice>): List<CurrencyPriceDetail> {
        val currentCurrencies = uiState.value.list
        val updatedCurrencies = mutableListOf<CurrencyPriceDetail>()
        for (currency in currentCurrencies) {
            val newPrice = newPrices.find { it.id == currency.id }
            if (newPrice == null) {
                updatedCurrencies.add(currency)
            } else {
                updatedCurrencies.add(currency.copy(priceUsd = newPrice.priceUsd))
            }
        }
        return updatedCurrencies
    }
}