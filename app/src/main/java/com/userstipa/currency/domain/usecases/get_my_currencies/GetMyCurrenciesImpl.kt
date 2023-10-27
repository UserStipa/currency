package com.userstipa.currency.domain.usecases.get_my_currencies

import com.userstipa.currency.data.api.CurrencyDto
import com.userstipa.currency.data.local.PreferencesKeys
import com.userstipa.currency.data.repository.Repository
import com.userstipa.currency.domain.Resource
import com.userstipa.currency.domain.mapper.Mapper
import com.userstipa.currency.domain.model.CurrencyPriceDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMyCurrenciesImpl @Inject constructor(
    private val repository: Repository,
    private val mapper: Mapper<CurrencyDto, CurrencyPriceDetail>
) : GetMyCurrencies {

    override suspend fun launch(): Flow<Resource<List<CurrencyPriceDetail>>> = flow {
        try {
            emit(Resource.Loading())
            val myCurrenciesIds = repository.getPreferences(PreferencesKeys.MY_CURRENCIES)
            if (myCurrenciesIds.isEmpty()) {
                emit(Resource.Success(emptyList()))
            } else {
                val networkResult =
                    repository.getRemoteCurrencies(myCurrenciesIds.joinToString(","))
                val data = networkResult.body()!!.data
                val remoteCurrencies = mapper.map(data)
                emit(Resource.Success(remoteCurrencies))
            }
        } catch (e: Throwable) {
            emit(Resource.Error(e))
        }
    }
}