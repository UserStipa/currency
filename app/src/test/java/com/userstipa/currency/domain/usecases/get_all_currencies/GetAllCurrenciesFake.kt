package com.userstipa.currency.domain.usecases.get_all_currencies

import com.userstipa.currency.domain.model.Currency
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAllCurrenciesFake : GetAllCurrencies {

    var launchResult: List<Currency>? = null
    var launchException: Throwable? = null

    override fun launch(): Flow<List<Currency>> = flow {
        launchException?.let { throw it }
        emit(launchResult!!)
    }
}