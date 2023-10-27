package com.userstipa.currency.testUtil

import com.userstipa.currency.domain.Resource
import com.userstipa.currency.domain.model.CurrencyPriceDetail
import com.userstipa.currency.domain.usecases.get_my_currencies.GetMyCurrencies
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class GetMyCurrenciesFake : GetMyCurrencies {

    private var flow = MutableSharedFlow<Resource<List<CurrencyPriceDetail>>>()

    suspend fun emit(value: Resource<List<CurrencyPriceDetail>>) {
        flow.emit(value)
    }

    override suspend fun launch(): Flow<Resource<List<CurrencyPriceDetail>>> {
        return flow
    }
}