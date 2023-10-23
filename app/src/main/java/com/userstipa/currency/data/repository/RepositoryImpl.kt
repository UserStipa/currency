package com.userstipa.currency.data.repository

import com.userstipa.currency.data.api.CryptocurrencyApi
import com.userstipa.currency.data.api.GetCurrenciesDto
import retrofit2.Response
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: CryptocurrencyApi
) : Repository {

    override suspend fun getRemoteCurrencies(vararg ids: String): Response<GetCurrenciesDto> {
        TODO("Not yet implemented")
    }

    override suspend fun getRemoteCurrencies(): Response<GetCurrenciesDto> {
        return api.getCurrencies()
    }
}