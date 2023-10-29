package com.userstipa.currency.domain.usecases.get_all_currencies

import com.userstipa.currency.domain.model.Currency
import kotlinx.coroutines.flow.Flow

interface GetAllCurrencies {

    fun launch(): Flow<List<Currency>>

}