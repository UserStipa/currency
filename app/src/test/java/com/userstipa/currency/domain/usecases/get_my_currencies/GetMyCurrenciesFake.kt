package com.userstipa.currency.domain.usecases.get_my_currencies

import com.userstipa.currency.domain.model.CurrencyPriceDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class GetMyCurrenciesFake : GetMyCurrencies {

    private val flow = MutableSharedFlow<List<CurrencyPriceDetail>>()
    var subscribeError: Throwable? = null

    suspend fun emit(value: List<CurrencyPriceDetail>) {
        flow.emit(value)
    }

    override suspend fun launch(): Flow<List<CurrencyPriceDetail>> {
        subscribeError?.let { throw it }
        return flow
    }
}