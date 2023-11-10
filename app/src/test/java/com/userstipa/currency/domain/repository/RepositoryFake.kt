package com.userstipa.currency.domain.repository

import com.userstipa.currency.data.api.get_currencies.WrapperCurrenciesDto
import com.userstipa.currency.data.api.get_currency_history.WrapperPriceTimeDto
import com.userstipa.currency.data.local.PreferencesKeys
import com.userstipa.currency.data.repository.Repository
import com.userstipa.currency.data.websocket.CurrencyPriceWrapperDto
import com.userstipa.currency.domain.model.HistoryRange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class RepositoryFake : Repository {

    var getRemoteCurrenciesResult: Response<WrapperCurrenciesDto>? = null
    var getRemoteCurrenciesException: Throwable? = null

    var getRemoteCurrencyHistoryResult: Response<WrapperPriceTimeDto>? = null
    var getRemoteCurrencyHistoryException: Throwable? = null

    var openWebSocketResult: CurrencyPriceWrapperDto? = null
    var openWebSocketException: Throwable? = null

    private val preferencesFake = mutableMapOf<String, Set<String>>()

    override suspend fun getRemoteCurrencies(ids: String): Response<WrapperCurrenciesDto> {
        getRemoteCurrenciesException?.let { throw it }
        return getRemoteCurrenciesResult!!
    }

    override suspend fun getRemoteCurrencies(): Response<WrapperCurrenciesDto> {
        getRemoteCurrenciesException?.let { throw it }
        return getRemoteCurrenciesResult!!
    }

    override suspend fun getRemoteCurrencyHistory(
        id: String,
        historyRange: HistoryRange
    ): Response<WrapperPriceTimeDto> {
        getRemoteCurrencyHistoryException?.let { throw it }
        return getRemoteCurrencyHistoryResult!!
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