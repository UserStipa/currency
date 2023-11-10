package com.userstipa.currency.data.repository

import com.userstipa.currency.data.api.get_currencies.WrapperCurrenciesDto
import com.userstipa.currency.data.api.get_currency_history.WrapperPriceTimeDto
import com.userstipa.currency.data.local.PreferencesKeys
import com.userstipa.currency.data.websocket.CurrencyPriceWrapperDto
import com.userstipa.currency.domain.model.HistoryRange
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface Repository {

    suspend fun getRemoteCurrencies(ids: String): Response<WrapperCurrenciesDto>

    suspend fun getRemoteCurrencies(): Response<WrapperCurrenciesDto>

    suspend fun getRemoteCurrencyHistory(id: String, historyRange: HistoryRange): Response<WrapperPriceTimeDto>

    suspend fun setPreferences(key: PreferencesKeys, value: Set<String>)

    suspend fun getPreferences(key: PreferencesKeys): Set<String>

    fun openWebSocket(ids: String): Flow<CurrencyPriceWrapperDto>


}