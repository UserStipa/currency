package com.userstipa.currency.domain.usecases.remove_currency

import com.userstipa.currency.data.local.PreferencesKeys
import com.userstipa.currency.data.repository.Repository
import com.userstipa.currency.domain.model.Currency
import javax.inject.Inject

class RemoveCurrencyImpl @Inject constructor(
    private val repository: Repository
) : RemoveCurrency {

    override suspend fun launch(currency: Currency) {
        val currentMyCurrencies = repository.getPreferences(PreferencesKeys.MY_CURRENCIES)
        val newSet = currentMyCurrencies.minus(currency.id)
        repository.setPreferences(PreferencesKeys.MY_CURRENCIES, newSet)
    }
}