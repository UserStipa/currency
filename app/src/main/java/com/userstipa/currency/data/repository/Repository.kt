package com.userstipa.currency.data.repository

import com.userstipa.currency.data.api.GetCurrenciesDto
import retrofit2.Response

interface Repository {

    suspend fun getRemoteCurrencies(vararg ids: String): Response<GetCurrenciesDto>

    suspend fun getRemoteCurrencies(): Response<GetCurrenciesDto>

}