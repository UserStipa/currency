package com.userstipa.currency.domain.usecases.get_currency_price_details

import com.userstipa.currency.domain.model.HistoryRange
import com.userstipa.currency.domain.model.PriceTime
import java.time.ZoneId
import java.time.ZonedDateTime

class FilterHistoryImpl : FilterHistory {

    override fun launch(
        history: List<PriceTime>,
        historyRange: HistoryRange
    ): List<PriceTime> {
        val currentTime = ZonedDateTime.now(ZoneId.systemDefault())
        val filter = when (historyRange) {
            HistoryRange.LAST_HOUR -> {
                currentTime.minusHours(1)
            }

            HistoryRange.LAST_DAY -> {
                currentTime.minusDays(1)
            }

            HistoryRange.LAST_WEEK -> {
                currentTime.minusWeeks(1)
            }

            HistoryRange.LAST_MONTH -> {
                currentTime.minusMonths(1)
            }

            HistoryRange.LAST_YEAR -> {
                currentTime.minusYears(1)
            }
        }
        return history.filter { priceTime ->
            priceTime.dateTime.isAfter(filter)
        }
    }
}