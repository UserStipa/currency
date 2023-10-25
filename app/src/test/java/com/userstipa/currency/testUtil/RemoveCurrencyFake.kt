package com.userstipa.currency.testUtil

import com.userstipa.currency.domain.model.Currency
import com.userstipa.currency.domain.usecases.remove_currency.RemoveCurrency

class RemoveCurrencyFake : RemoveCurrency {

    var result: Currency? = null

    override suspend fun launch(currency: Currency) {
        result = currency
    }
}