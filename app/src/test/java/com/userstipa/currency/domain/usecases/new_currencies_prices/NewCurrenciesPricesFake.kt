package com.userstipa.currency.domain.usecases.new_currencies_prices

import com.userstipa.currency.domain.model.CurrencyPrice
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NewCurrenciesPricesFake : NewCurrenciesPrices {

    var subscribeResult: List<CurrencyPrice>? = null
    var subscribeError: Throwable? = null

    override suspend fun subscribe(): Flow<List<CurrencyPrice>> = flow {
        subscribeError?.let { throw it }
        emit(subscribeResult!!)
    }
}