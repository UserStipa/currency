package com.userstipa.currency.testUtil

import com.userstipa.currency.data.api.GetCurrenciesDto
import com.userstipa.currency.data.repository.Repository
import retrofit2.Response

class RepositoryFake : Repository {

    var result: Response<GetCurrenciesDto>? = null

    override suspend fun getRemoteCurrencies(vararg ids: String): Response<GetCurrenciesDto> {
        return result!!
    }

    override suspend fun getRemoteCurrencies(): Response<GetCurrenciesDto> {
        return result!!
    }
}