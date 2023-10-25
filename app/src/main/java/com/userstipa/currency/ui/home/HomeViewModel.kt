package com.userstipa.currency.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.userstipa.currency.di.dispatchers.DispatcherProvider
import com.userstipa.currency.domain.Resource
import com.userstipa.currency.domain.usecases.get_my_currencies.GetMyCurrencies
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getMyCurrencies: GetMyCurrencies,
    private val dispatcher: DispatcherProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun fetchData() {
        viewModelScope.launch(dispatcher.io) {
            getMyCurrencies.launch().collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _uiState.update { it.copy(error = result.exception.message) }
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
}