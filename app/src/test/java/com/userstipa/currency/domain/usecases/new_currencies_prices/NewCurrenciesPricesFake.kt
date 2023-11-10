package com.userstipa.currency.domain.usecases.new_currencies_prices

import com.userstipa.currency.domain.model.Price
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class NewCurrenciesPricesFake : NewCurrenciesPrices {

    private val flow = MutableSharedFlow<List<Price>>()
    var subscribeError: Throwable? = null

    suspend fun emit(value: List<Price>) {
        flow.emit(value)
    }

    override suspend fun subscribe(): Flow<List<Price>> {
        subscribeError?.let { throw it }
        return flow
    }
}