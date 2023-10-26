package com.userstipa.currency.domain.usecases.new_currencies_prices

import com.userstipa.currency.domain.model.CurrencyPrice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface NewCurrenciesPrices {

    suspend fun subscribe(scope: CoroutineScope): Flow<List<CurrencyPrice>>

    fun unsubscribe()
}