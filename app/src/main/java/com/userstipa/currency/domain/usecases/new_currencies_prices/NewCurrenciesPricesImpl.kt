package com.userstipa.currency.domain.usecases.new_currencies_prices

import com.userstipa.currency.data.local.PreferencesKeys
import com.userstipa.currency.data.repository.Repository
import com.userstipa.currency.data.websocket.CurrencyPriceDto
import com.userstipa.currency.domain.Resource
import com.userstipa.currency.domain.mapper.Mapper
import com.userstipa.currency.domain.model.CurrencyPrice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewCurrenciesPricesImpl @Inject constructor(
    private val repository: Repository,
    private val mapper: Mapper<CurrencyPriceDto, CurrencyPrice>
) : NewCurrenciesPrices {

    override suspend fun subscribe(scope: CoroutineScope): Flow<Resource<List<CurrencyPrice>>> =
        flow {
            try {
                val myCurrenciesIds = repository.getPreferences(PreferencesKeys.MY_CURRENCIES)
                if (myCurrenciesIds.isEmpty()) {
                    emit(Resource.Success(emptyList()))
                } else {
                    val query = myCurrenciesIds.joinToString(",")
                    val networkResult = repository.openWebSocket(scope, query)
                        .map { networkResult ->
                            mapper.map(networkResult.data)
                        }
                        .map { data ->
                            Resource.Success(data)
                        }
                    emitAll(networkResult)
                }
            } catch (e: Throwable) {
                emit(Resource.Error(e))
            }
        }

    override fun unsubscribe() {
        repository.closeWebSocket()
    }
}