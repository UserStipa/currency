package com.userstipa.currency.ui.home

import androidx.lifecycle.ViewModel
import com.userstipa.currency.di.dispatchers.DispatcherProvider
import com.userstipa.currency.domain.usecases.subscribe_my_currencies.SubscribeMyCurrencies
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
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

class HomeViewModel @Inject constructor(
    private val subscribeMyCurrencies: SubscribeMyCurrencies,
    private val dispatcher: DispatcherProvider
) : ViewModel() {

    private val webSocketScope = CoroutineScope(Job())
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun subscribeData() {
        webSocketScope.launch(dispatcher.io) {
            subscribeMyCurrencies.launch()
                .onStart {
                    _uiState.update { it.copy(isLoading = true, error = null) }
                }
                .catch { error ->
                    when (error) {
                        is IOException -> _uiState.update {
                            HomeUiState(
                                isLoading = false,
                                error = "Lost internet connection"
                            )
                        }
                        else -> _uiState.update {
                            HomeUiState(
                                isLoading = false,
                                error = error.message
                            )
                        }
                    }
                }
                .collectLatest { result ->
                    _uiState.update { HomeUiState(isLoading = false, list = result) }
                }
        }
    }

    fun unsubscribeData() {
        webSocketScope.coroutineContext.cancelChildren()
    }

    override fun onCleared() {
        super.onCleared()
        webSocketScope.cancel()
    }
}