package com.userstipa.currency.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.userstipa.currency.di.dispatchers_module.DispatcherProvider
import com.userstipa.currency.domain.model.HistoryRange
import com.userstipa.currency.domain.model.HistoryRange.LAST_HOUR
import com.userstipa.currency.domain.usecases.get_currency_price_details.GetCurrencyPriceDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val getCurrencyPriceDetails: GetCurrencyPriceDetails
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState: StateFlow<DetailsUiState> = _uiState

    fun fetchData(currencyId: String, historyRange: HistoryRange = LAST_HOUR) {
        viewModelScope.launch(dispatcher.io) {
            getCurrencyPriceDetails.launch(currencyId, historyRange)
                .onStart {
                    _uiState.update {
                        it.copy(isLoading = true, error = null)
                    }
                }
                .catch { error ->
                    when (error) {
                        is IOException -> _uiState.update {
                            DetailsUiState(
                                isLoading = false,
                                error = "Lost internet connection"
                            )
                        }

                        else -> _uiState.update {
                            DetailsUiState(
                                isLoading = false,
                                error = error.message
                            )
                        }
                    }
                }
                .collectLatest { result ->
                    _uiState.update { DetailsUiState(currency = result) }
                }
        }
    }

}