package com.userstipa.currency.domain.usecases.get_my_currencies

import com.userstipa.currency.domain.Resource
import com.userstipa.currency.domain.model.CurrencyPriceDetail
import kotlinx.coroutines.flow.Flow


interface GetMyCurrencies {

    suspend fun launch(): Flow<Resource<List<CurrencyPriceDetail>>>
}