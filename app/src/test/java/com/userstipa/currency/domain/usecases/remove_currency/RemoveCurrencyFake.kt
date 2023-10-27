package com.userstipa.currency.domain.usecases.remove_currency

import com.userstipa.currency.domain.model.Currency

class RemoveCurrencyFake : RemoveCurrency {

    var result: Currency? = null

    override suspend fun launch(currency: Currency) {
        result = currency
    }
}