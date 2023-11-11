package com.userstipa.currency.domain.usecases.get_currency_price_details

import com.userstipa.currency.domain.model.HistoryRange
import com.userstipa.currency.domain.model.PriceTime

interface FilterHistory {

    fun launch(
        history: List<PriceTime>,
        historyRange: HistoryRange
    ): List<PriceTime>
}