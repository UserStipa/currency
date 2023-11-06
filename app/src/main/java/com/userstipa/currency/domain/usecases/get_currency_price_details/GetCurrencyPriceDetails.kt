package com.userstipa.currency.domain.usecases.get_currency_price_details

import com.userstipa.currency.domain.model.CurrencyPriceDetails
import com.userstipa.currency.domain.model.HistoryRange
import kotlinx.coroutines.flow.Flow

interface GetCurrencyPriceDetails {

    fun launch(id: String, historyRange: HistoryRange): Flow<CurrencyPriceDetails>

}