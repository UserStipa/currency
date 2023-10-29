package com.userstipa.currency.data.repository

import com.userstipa.currency.data.api.GetCurrenciesDto
import com.userstipa.currency.data.local.PreferencesKeys
import com.userstipa.currency.data.websocket.CurrencyPriceWrapperDto
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface Repository {

    suspend fun getRemoteCurrencies(ids: String): Response<GetCurrenciesDto>

    suspend fun getRemoteCurrencies(): Response<GetCurrenciesDto>

    suspend fun setPreferences(key: PreferencesKeys, value: Set<String>)

    suspend fun getPreferences(key: PreferencesKeys): Set<String>

    fun openWebSocket(ids: String): Flow<CurrencyPriceWrapperDto>


}