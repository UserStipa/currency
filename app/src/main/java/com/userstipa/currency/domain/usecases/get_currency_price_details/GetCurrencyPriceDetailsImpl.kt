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
import javax.inject.Inject

class GetCurrencyPriceDetailsImpl @Inject constructor(
    private val repository: Repository,
    private val mapperCurrency: Mapper<CurrencyDto, CurrencyPriceDetails>,
    private val mapperPriceTime: Mapper<PriceTimeDto, PriceTime>
) : GetCurrencyPriceDetails {

    override fun launch(id: String, historyRange: HistoryRange): Flow<CurrencyPriceDetails> = flow {
        val currency = repository.getRemoteCurrencies(id).let { response ->
            val data = response.body()!!.data
            mapperCurrency.map(data).first()
        }

        val history = repository.getRemoteCurrencyHistory(id, historyRange).let { response ->
            val data = response.body()!!.data
            mapperPriceTime.map(data)
        }

        val result = currency.copy(history = history)
        emit(result)
    }
}