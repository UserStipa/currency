package com.userstipa.currency.domain.usecases.add_currency

import com.userstipa.currency.domain.model.Currency

class AddCurrencyFake : AddCurrency {

    var result: Currency? = null

    override suspend fun launch(currency: Currency) {
        result = currency
    }
}