package com.userstipa.currency.domain.usecases.subscribe_my_currencies

import com.userstipa.currency.domain.model.CurrencyPrice
import kotlinx.coroutines.flow.Flow

interface SubscribeMyCurrencies {

    fun launch(): Flow<List<CurrencyPrice>>

}