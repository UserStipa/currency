package com.userstipa.currency.domain.usecases.add_currency

import com.userstipa.currency.data.local.PreferencesKeys
import com.userstipa.currency.data.repository.Repository
import com.userstipa.currency.domain.model.Currency
import javax.inject.Inject

class AddCurrencyImpl @Inject constructor(
    private val repository: Repository
) : AddCurrency {

    override suspend fun launch(currency: Currency) {
        val currentMyCurrencies = repository.getPreferences(PreferencesKeys.MY_CURRENCIES)
        currentMyCurrencies.toMutableList().add(currency.id)
        repository.setPreferences(PreferencesKeys.MY_CURRENCIES, currentMyCurrencies.toList())
    }
}