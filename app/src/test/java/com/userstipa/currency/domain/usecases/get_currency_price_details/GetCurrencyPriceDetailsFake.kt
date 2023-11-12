package com.userstipa.currency.domain.usecases.get_currency_price_details

import com.userstipa.currency.domain.model.CurrencyPriceDetails
import com.userstipa.currency.domain.model.HistoryRange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class GetCurrencyPriceDetailsFake : GetCurrencyPriceDetails {

    private val flow = MutableSharedFlow<CurrencyPriceDetails>()
    var error: Throwable? = null

    suspend fun emit(value: CurrencyPriceDetails) {
        flow.emit(value)
    }

    override fun launch(id: String, historyRange: HistoryRange): Flow<CurrencyPriceDetails> = flow {
        error?.let { throw it }
        emitAll(flow)
    }
}