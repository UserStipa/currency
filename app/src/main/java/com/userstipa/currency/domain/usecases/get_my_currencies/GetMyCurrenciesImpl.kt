package com.userstipa.currency.domain.usecases.get_my_currencies

import com.userstipa.currency.data.api.get_currencies.CurrencyDto
import com.userstipa.currency.data.local.PreferencesKeys
import com.userstipa.currency.data.repository.Repository
import com.userstipa.currency.domain.mapper.Mapper
import com.userstipa.currency.domain.model.CurrencyPrice
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMyCurrenciesImpl @Inject constructor(
    private val repository: Repository,
    private val mapper: Mapper<CurrencyDto, CurrencyPrice>
) : GetMyCurrencies {

    override suspend fun launch(): Flow<List<CurrencyPrice>> = flow {
        val myCurrenciesIds = repository.getPreferences(PreferencesKeys.MY_CURRENCIES)
        if (myCurrenciesIds.isEmpty()) {
            emit(emptyList())
        } else {
            val query = myCurrenciesIds.joinToString(",")
            val networkResult = repository.getRemoteCurrencies(query)
            val data = networkResult.body()!!.data
            val remoteCurrencies = mapper.map(data)
            emit(remoteCurrencies)
        }
    }
}