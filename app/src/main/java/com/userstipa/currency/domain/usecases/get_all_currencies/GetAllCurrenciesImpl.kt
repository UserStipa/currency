package com.userstipa.currency.domain.usecases.get_all_currencies

import com.userstipa.currency.data.api.CurrencyDto
import com.userstipa.currency.data.local.PreferencesKeys
import com.userstipa.currency.data.repository.Repository
import com.userstipa.currency.domain.Resource
import com.userstipa.currency.domain.mapper.Mapper
import com.userstipa.currency.domain.model.Currency
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllCurrenciesImpl @Inject constructor(
    private val repository: Repository,
    private val mapper: Mapper<CurrencyDto, Currency>
) : GetAllCurrencies {

    override fun launch(): Flow<Resource<List<Currency>>> = flow {
        try {
            emit(Resource.Loading())
            val networkResult = repository.getRemoteCurrencies()
            val data = networkResult.body()!!.data
            val remoteCurrencies = mapper.map(data)

            val myCurrenciesIds = repository.getPreferences(PreferencesKeys.MY_CURRENCIES)
            remoteCurrencies.forEach { remoteCurrency ->
                remoteCurrency.isEnableCheckbox = (myCurrenciesIds.contains(remoteCurrency.id))
            }
            emit(Resource.Success(remoteCurrencies))
        } catch (e: Throwable) {
            emit(Resource.Error(e))
        }
    }


}