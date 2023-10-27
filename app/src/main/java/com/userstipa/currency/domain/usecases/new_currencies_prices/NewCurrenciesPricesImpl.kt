package com.userstipa.currency.domain.usecases.new_currencies_prices

import com.userstipa.currency.data.local.PreferencesKeys
import com.userstipa.currency.data.repository.Repository
import com.userstipa.currency.data.websocket.CurrencyPriceDto
import com.userstipa.currency.domain.mapper.Mapper
import com.userstipa.currency.domain.model.CurrencyPrice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewCurrenciesPricesImpl @Inject constructor(
    private val repository: Repository,
    private val mapper: Mapper<CurrencyPriceDto, CurrencyPrice>
) : NewCurrenciesPrices {

    override suspend fun subscribe(scope: CoroutineScope): Flow<List<CurrencyPrice>> {
        val myCurrenciesIds = repository.getPreferences(PreferencesKeys.MY_CURRENCIES)
        return if (myCurrenciesIds.isEmpty()) {
            emptyFlow()
        } else {
            val newPrices = repository.openWebSocket(scope, myCurrenciesIds.joinToString(","))
            return newPrices.map { value -> mapper.map(value.data) }
        }
    }

    override fun unsubscribe() {
        repository.closeWebSocket()
    }
}