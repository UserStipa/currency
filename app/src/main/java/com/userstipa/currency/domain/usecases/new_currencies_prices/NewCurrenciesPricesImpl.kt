package com.userstipa.currency.domain.usecases.new_currencies_prices

import com.userstipa.currency.data.local.PreferencesKeys
import com.userstipa.currency.data.repository.Repository
import com.userstipa.currency.data.websocket.CurrencyPriceWrapperDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewCurrenciesPricesImpl @Inject constructor(
    private val repository: Repository
) : NewCurrenciesPrices {

    override suspend fun subscribe(scope: CoroutineScope): Flow<CurrencyPriceWrapperDto> {
        val myCurrenciesIds = repository.getPreferences(PreferencesKeys.MY_CURRENCIES)
        return repository.openWebSocket(scope, myCurrenciesIds.joinToString(","))
    }

    override fun unsubscribe() {
        repository.closeWebSocket()
    }
}