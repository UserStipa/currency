package com.userstipa.currency.domain.usecases.new_currencies_prices

import com.userstipa.currency.domain.model.Price
import kotlinx.coroutines.flow.Flow

interface NewCurrenciesPrices {

    suspend fun subscribe(): Flow<List<Price>>

}