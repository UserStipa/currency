package com.userstipa.currency.domain.usecases.get_my_currencies

import com.userstipa.currency.domain.model.CurrencyPrice
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class GetMyCurrenciesFake : GetMyCurrencies {

    private val flow = MutableSharedFlow<List<CurrencyPrice>>()
    var subscribeError: Throwable? = null

    suspend fun emit(value: List<CurrencyPrice>) {
        flow.emit(value)
    }

    override suspend fun launch(): Flow<List<CurrencyPrice>> {
        subscribeError?.let { throw it }
        return flow
    }
}