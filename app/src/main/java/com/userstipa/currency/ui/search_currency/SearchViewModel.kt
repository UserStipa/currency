package com.userstipa.currency.ui.search_currency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.userstipa.currency.App
import com.userstipa.currency.domain.Resource
import com.userstipa.currency.domain.usecases.get_remote_currencies.GetRemoteCurrencies
import com.userstipa.currency.ui.home.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val getRemoteCurrencies: GetRemoteCurrencies
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        fetchData()
    }

    fun fetchData() {
        getRemoteCurrencies.launch().onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _uiState.value = HomeUiState(error = result.exception.message)
                    throw result.exception
                }

                is Resource.Loading -> {
                    _uiState.value = HomeUiState(isLoading = true)
                }

                is Resource.Success -> {
                    _uiState.value = HomeUiState(list = result.data)
                }
            }
        }.launchIn(viewModelScope)
    }

    class Factory : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            val application =
                checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
            val getRemoteCurrencies = (application as App).appComponent.getRemoteCurrencies()
            return SearchViewModel(getRemoteCurrencies) as T
        }
    }

}