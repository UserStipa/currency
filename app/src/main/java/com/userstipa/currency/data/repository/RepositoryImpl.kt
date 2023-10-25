package com.userstipa.currency.data.repository

import com.userstipa.currency.data.api.CryptocurrencyApi
import com.userstipa.currency.data.api.GetCurrenciesDto
import com.userstipa.currency.data.local.Preferences
import com.userstipa.currency.data.local.PreferencesKeys
import retrofit2.Response
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: CryptocurrencyApi,
    private val preferences: Preferences
) : Repository {

    override suspend fun getRemoteCurrencies(ids: String): Response<GetCurrenciesDto> {
        return api.getCurrencies(ids)
    }

    override suspend fun getRemoteCurrencies(): Response<GetCurrenciesDto> {
        return api.getCurrencies()
    }

    override suspend fun setPreferences(key: PreferencesKeys, value: Set<String>) {
        preferences.setPreferences(key, value)
    }

    override suspend fun getPreferences(key: PreferencesKeys): Set<String> {
        return preferences.getPreferences(key)
    }
}