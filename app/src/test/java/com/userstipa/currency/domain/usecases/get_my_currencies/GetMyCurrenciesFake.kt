package com.userstipa.currency.domain.usecases.get_my_currencies

import com.userstipa.currency.domain.model.CurrencyPriceDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMyCurrenciesFake : GetMyCurrencies {

    var launchResult: List<CurrencyPriceDetail>? = null
    var launchException: Throwable? = null

    override suspend fun launch(): Flow<List<CurrencyPriceDetail>> = flow {
        launchException?.let { throw it }
        emit(launchResult!!)
    }
}