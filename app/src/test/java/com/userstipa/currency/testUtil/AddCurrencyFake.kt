package com.userstipa.currency.testUtil

import com.userstipa.currency.domain.model.Currency
import com.userstipa.currency.domain.usecases.add_currency.AddCurrency

class AddCurrencyFake : AddCurrency {

    var result: Currency? = null

    override suspend fun launch(currency: Currency) {
        result = currency
    }
}