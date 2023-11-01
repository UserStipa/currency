package com.userstipa.currency.domain.usecases.subscribe_my_currencies

import com.userstipa.currency.domain.model.CurrencyPriceDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class SubscribeMyCurrenciesFake : SubscribeMyCurrencies {

    private val flow = MutableSharedFlow<List<CurrencyPriceDetail>>()
    var launchException: Throwable? = null

    suspend fun emit(value: List<CurrencyPriceDetail>) {
        flow.emit(value)
    }

    override fun launch(): Flow<List<CurrencyPriceDetail>> {
        launchException?.let { throw it }
        return flow
    }
}