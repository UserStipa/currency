package com.userstipa.currency.ui.search_currency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.userstipa.currency.di.dispatchers.DispatcherProvider
import com.userstipa.currency.domain.model.Currency
import com.userstipa.currency.domain.usecases.add_currency.AddCurrency
import com.userstipa.currency.domain.usecases.get_all_currencies.GetAllCurrencies
import com.userstipa.currency.domain.usecases.remove_currency.RemoveCurrency
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val getAllCurrencies: GetAllCurrencies,
    private val addCurrency: AddCurrency,
    private val removeCurrency: RemoveCurrency,
    private val dispatcher: DispatcherProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    fun fetchData() {
        viewModelScope.launch(dispatcher.io) {
            getAllCurrencies.launch()
                .onStart {
                    _uiState.update { it.copy(isLoading = true) }
                }
                .catch { error ->
                    when (error) {
                        is IOException -> _uiState.update {
                            SearchUiState(
                                isLoading = false,
                                error = "Lost internet connection"
                            )
                        }

                        else -> {
                            SearchUiState(
                                isLoading = false,
                                error = error.message
                            )
                        }
                    }
                }
                .collectLatest { result ->
                    _uiState.update { it.copy(isLoading = false, list = result) }
                }
        }
    }

    fun addCurrency(currency: Currency) {
        viewModelScope.launch(dispatcher.io) {
            addCurrency.launch(currency)
        }
    }

    fun removeCurrency(currency: Currency) {
        viewModelScope.launch(dispatcher.io) {
            removeCurrency.launch(currency)
        }
    }

}