package com.userstipa.currency.domain.repository

import com.userstipa.currency.data.api.GetCurrenciesDto
import com.userstipa.currency.data.local.PreferencesKeys
import com.userstipa.currency.data.repository.Repository
import com.userstipa.currency.data.websocket.CurrencyPriceWrapperDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class RepositoryFake : Repository {

    var getRemoteCurrenciesResult: Response<GetCurrenciesDto>? = null
    var getRemoteCurrenciesException: Throwable? = null
    var openWebSocketResult: CurrencyPriceWrapperDto? = null
    var openWebSocketException: Throwable? = null

    private val preferencesFake = mutableMapOf<String, Set<String>>()

    override suspend fun getRemoteCurrencies(ids: String): Response<GetCurrenciesDto> {
        getRemoteCurrenciesException?.let { throw it }
        return getRemoteCurrenciesResult!!
    }

    override suspend fun getRemoteCurrencies(): Response<GetCurrenciesDto> {
        getRemoteCurrenciesException?.let { throw it }
        return getRemoteCurrenciesResult!!
    }

    override suspend fun setPreferences(key: PreferencesKeys, value: Set<String>) {
        preferencesFake[key.name] = value
    }

    override suspend fun getPreferences(key: PreferencesKeys): Set<String> {
        return preferencesFake[key.name] ?: emptySet()
    }

    override fun openWebSocket(ids: String): Flow<CurrencyPriceWrapperDto> = flow {
        openWebSocketException?.let { throw it }
        emit(openWebSocketResult!!)
    }
}