package com.userstipa.currency.domain.usecases.get_my_currencies

import com.userstipa.currency.domain.model.CurrencyPrice
import kotlinx.coroutines.flow.Flow


interface GetMyCurrencies {

    suspend fun launch(): Flow<List<CurrencyPrice>>
}