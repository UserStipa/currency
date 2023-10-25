package com.userstipa.currency.domain.usecases.remove_currency

import com.userstipa.currency.domain.model.Currency

interface RemoveCurrency {

    suspend fun launch(currency: Currency)

}