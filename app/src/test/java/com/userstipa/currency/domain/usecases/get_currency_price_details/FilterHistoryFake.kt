package com.userstipa.currency.domain.usecases.get_currency_price_details

import com.userstipa.currency.domain.model.HistoryRange
import com.userstipa.currency.domain.model.PriceTime
import java.time.ZonedDateTime

class FilterHistoryFake : FilterHistory {

    var currentTimeTest: ZonedDateTime? = null

    override fun launch(history: List<PriceTime>, historyRange: HistoryRange): List<PriceTime> {
        val filter = when (historyRange) {
            HistoryRange.LAST_HOUR -> {
                currentTimeTest!!.minusHours(1)
            }

            HistoryRange.LAST_DAY -> {
                currentTimeTest!!.minusDays(1)
            }

            HistoryRange.LAST_WEEK -> {
                currentTimeTest!!.minusWeeks(1)
            }

            HistoryRange.LAST_MONTH -> {
                currentTimeTest!!.minusMonths(1)
            }

            HistoryRange.LAST_YEAR -> {
                currentTimeTest!!.minusYears(1)
            }
        }
        return history.filter { priceTime ->
            priceTime.dateTime.isAfter(filter)
        }
    }
}