package com.userstipa.currency.domain.usecases.new_currencies_prices

import com.userstipa.currency.domain.model.CurrencyPrice
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class NewCurrenciesPricesFake : NewCurrenciesPrices {

    private val flow = MutableSharedFlow<List<CurrencyPrice>>()
    var subscribeError: Throwable? = null

    suspend fun emit(value: List<CurrencyPrice>) {
        flow.emit(value)
    }

    override suspend fun subscribe(): Flow<List<CurrencyPrice>> {
        subscribeError?.let { throw it }
        return flow
    }
}