package com.userstipa.currency.domain.usecases.get_currency

import com.userstipa.currency.data.api.CurrencyDto
import com.userstipa.currency.data.repository.Repository
import com.userstipa.currency.domain.mapper.Mapper
import com.userstipa.currency.domain.model.Currency
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCurrencyImpl @Inject constructor(
    private val repository: Repository,
    private val mapper: Mapper<CurrencyDto, Currency>
) : GetCurrency {

    override fun launch(id: String): Flow<Currency> = flow {
        val networkResult = repository.getRemoteCurrencies(id)
        val data = networkResult.body()!!.data
        val result = mapper.map(data).first()
        emit(result)
    }
}