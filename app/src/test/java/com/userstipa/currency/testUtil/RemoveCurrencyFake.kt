package com.userstipa.currency.testUtil

import com.userstipa.currency.domain.model.Currency
import com.userstipa.currency.domain.usecases.remove_currency.RemoveCurrency

class RemoveCurrencyFake : RemoveCurrency {

    override suspend fun launch(currency: Currency) {
        TODO("Not yet implemented")
    }
}