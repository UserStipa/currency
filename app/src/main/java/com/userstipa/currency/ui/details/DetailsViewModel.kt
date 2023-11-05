package com.userstipa.currency.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.userstipa.currency.di.dispatchers.DispatcherProvider
import com.userstipa.currency.domain.usecases.get_currency.GetCurrency
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val getCurrency: GetCurrency
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState: StateFlow<DetailsUiState> = _uiState

    fun fetchData(currencyId: String) {
        viewModelScope.launch(dispatcher.io) {
            getCurrency.launch(currencyId)
                .onStart {
                    _uiState.update {
                        it.copy(isLoading = true, error = null)
                    }
                }
                .catch { error ->
                    _uiState.update { DetailsUiState(error = error.message) }
                }
                .collectLatest { result ->
                    _uiState.update { DetailsUiState(currency = result) }
                }
        }
    }

}