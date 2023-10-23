package com.userstipa.currency.ui.search_currency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.userstipa.currency.domain.Resource
import com.userstipa.currency.domain.usecases.get_remote_currencies.GetRemoteCurrencies
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val getRemoteCurrencies: GetRemoteCurrencies
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState

    init {
        fetchData()
    }

    private fun fetchData() {
        getRemoteCurrencies.launch().onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _uiState.value = SearchUiState(error = result.exception.message)
                }

                is Resource.Loading -> {
                    _uiState.value = SearchUiState(isLoading = true)
                }

                is Resource.Success -> {
                    _uiState.value = SearchUiState(list = result.data)
                }
            }
        }.launchIn(viewModelScope)
    }

}