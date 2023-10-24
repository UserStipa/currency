package com.userstipa.currency.testUtil

import com.userstipa.currency.domain.Resource
import com.userstipa.currency.domain.model.Currency
import com.userstipa.currency.domain.usecases.get_remote_currencies.GetRemoteCurrencies
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class GetRemoteCurrenciesFake : GetRemoteCurrencies {

    private var flow = MutableSharedFlow<Resource<List<Currency>>>()

    suspend fun emit(value: Resource<List<Currency>>) {
        flow.emit(value)
    }

    override fun launch(): Flow<Resource<List<Currency>>> {
        return flow
    }
}