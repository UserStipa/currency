package com.userstipa.currency.domain.usecases.get_all_currencies

import com.userstipa.currency.data.api.get_currencies.CurrencyDto
import com.userstipa.currency.data.local.PreferencesKeys
import com.userstipa.currency.data.repository.Repository
import com.userstipa.currency.domain.mapper.Mapper
import com.userstipa.currency.domain.model.Currency
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllCurrenciesImpl @Inject constructor(
    private val repository: Repository,
    private val mapper: Mapper<CurrencyDto, Currency>
) : GetAllCurrencies {

    override fun launch(): Flow<List<Currency>> = flow {
        val networkResult = repository.getRemoteCurrencies()
        val data = networkResult.body()!!.data
        val myCurrenciesIds = repository.getPreferences(PreferencesKeys.MY_CURRENCIES)
        val currencies = mapper.map(data)
        currencies.forEach { currency ->
            currency.isEnableCheckbox = (myCurrenciesIds.contains(currency.id))
        }
        emit(currencies)
    }


}