package com.userstipa.currency.domain.usecases.subscribe_my_currencies

import com.userstipa.currency.domain.model.CurrencyPrice
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class SubscribeMyCurrenciesFake : SubscribeMyCurrencies {

    private val flow = MutableSharedFlow<List<CurrencyPrice>>()
    var launchException: Throwable? = null

    suspend fun emit(value: List<CurrencyPrice>) {
        flow.emit(value)
    }

    override fun launch(): Flow<List<CurrencyPrice>> = flow {
        launchException?.let { throw it }
        emitAll(flow)
    }
}