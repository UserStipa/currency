package com.userstipa.currency.domain.usecases.new_currencies_prices

import com.userstipa.currency.domain.model.CurrencyPrice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.takeWhile

class NewCurrenciesPricesFake : NewCurrenciesPrices {

    val webSocketFlow = MutableSharedFlow<List<CurrencyPrice>>()
    private var isWebSocketFlowOpen = true

    override suspend fun subscribe(scope: CoroutineScope): Flow<List<CurrencyPrice>> {
        return webSocketFlow.takeWhile { isWebSocketFlowOpen }
    }

    override fun unsubscribe() {
        isWebSocketFlowOpen = false
    }
}