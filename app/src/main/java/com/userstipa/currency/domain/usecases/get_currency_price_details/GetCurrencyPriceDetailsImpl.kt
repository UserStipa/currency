package com.userstipa.currency.domain.usecases.get_currency_price_details

import com.userstipa.currency.data.api.get_currencies.CurrencyDto
import com.userstipa.currency.data.api.get_currency_history.PriceTimeDto
import com.userstipa.currency.data.repository.Repository
import com.userstipa.currency.domain.mapper.Mapper
import com.userstipa.currency.domain.model.CurrencyPriceDetails
import com.userstipa.currency.domain.model.HistoryRange
import com.userstipa.currency.domain.model.PriceTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject

class GetCurrencyPriceDetailsImpl @Inject constructor(
    private val repository: Repository,
    private val mapperCurrency: Mapper<CurrencyDto, CurrencyPriceDetails>,
    private val mapperPriceTime: Mapper<PriceTimeDto, PriceTime>
) : GetCurrencyPriceDetails {

    //TODO Check empty history

    override fun launch(id: String, historyRange: HistoryRange): Flow<CurrencyPriceDetails> = flow {
        val currency = repository.getRemoteCurrencies(id).let { response ->
            val data = response.body()!!.data
            mapperCurrency.map(data).first()
        }

        val history = repository.getRemoteCurrencyHistory(id, historyRange).let { response ->
            val data = response.body()!!.data
            val priceTime = mapperPriceTime.map(data)
            filterHistory(priceTime, historyRange)
        }

        val result = currency.copy(history = history)
        emit(result)
    }

    private fun filterHistory(
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
        return history.filter { price ->
            price.dateTime.isAfter(filter)
        }
    }
}