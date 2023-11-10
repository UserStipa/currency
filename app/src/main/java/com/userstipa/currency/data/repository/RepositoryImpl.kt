package com.userstipa.currency.data.repository

import com.userstipa.currency.data.api.CryptocurrencyApi
import com.userstipa.currency.data.api.get_currencies.WrapperCurrenciesDto
import com.userstipa.currency.data.api.get_currency_history.WrapperPriceTimeDto
import com.userstipa.currency.data.local.Preferences
import com.userstipa.currency.data.local.PreferencesKeys
import com.userstipa.currency.data.websocket.CryptocurrencyWebSocket
import com.userstipa.currency.data.websocket.CurrencyPriceWrapperDto
import com.userstipa.currency.domain.model.HistoryRange
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: CryptocurrencyApi,
    private val websocket: CryptocurrencyWebSocket,
    private val preferences: Preferences
) : Repository {

    override suspend fun getRemoteCurrencies(ids: String): Response<WrapperCurrenciesDto> {
        return api.getCurrencies(ids)
    }

    override suspend fun getRemoteCurrencies(): Response<WrapperCurrenciesDto> {
        return api.getCurrencies()
    }

    override suspend fun getRemoteCurrencyHistory(id: String, historyRange: HistoryRange): Response<WrapperPriceTimeDto> {
        return api.getCurrencyHistory(id, historyRange.timeInterval)
    }

    override suspend fun setPreferences(key: PreferencesKeys, value: Set<String>) {
        preferences.setPreferences(key, value)
    }

    override suspend fun getPreferences(key: PreferencesKeys): Set<String> {
        return preferences.getPreferences(key)
    }

    override fun openWebSocket(ids: String): Flow<CurrencyPriceWrapperDto> {
        return websocket.open(ids)
    }

}