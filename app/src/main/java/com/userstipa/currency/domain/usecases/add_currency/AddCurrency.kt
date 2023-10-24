package com.userstipa.currency.domain.usecases.add_currency

import com.userstipa.currency.domain.model.Currency

interface AddCurrency {

    suspend fun launch(currency: Currency)

}