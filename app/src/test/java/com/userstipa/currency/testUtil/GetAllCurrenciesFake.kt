package com.userstipa.currency.testUtil

import com.userstipa.currency.domain.Resource
import com.userstipa.currency.domain.model.Currency
import com.userstipa.currency.domain.usecases.get_all_currencies.GetAllCurrencies
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class GetAllCurrenciesFake : GetAllCurrencies {

    private var flow = MutableSharedFlow<Resource<List<Currency>>>()

    suspend fun emit(value: Resource<List<Currency>>) {
        flow.emit(value)
    }

    override fun launch(): Flow<Resource<List<Currency>>> {
        return flow
    }
}