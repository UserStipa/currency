package com.userstipa.currency.domain.usecases.get_currency

import com.userstipa.currency.domain.model.Currency
import kotlinx.coroutines.flow.Flow

interface GetCurrency {

    fun launch(id: String): Flow<Currency>

}