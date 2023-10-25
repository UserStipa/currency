package com.userstipa.currency.testUtil

import com.userstipa.currency.data.api.GetCurrenciesDto
import com.userstipa.currency.data.local.PreferencesKeys
import com.userstipa.currency.data.repository.Repository
import retrofit2.Response

class RepositoryFake : Repository {

    var result: Response<GetCurrenciesDto>? = null
    var ids: String? = null

    private val preferencesFake = mutableMapOf<String, Set<String>>()

    override suspend fun getRemoteCurrencies(ids: String): Response<GetCurrenciesDto> {
        this.ids = ids
        println("set ids")
        return result!!
    }

    override suspend fun getRemoteCurrencies(): Response<GetCurrenciesDto> {
        return result!!
    }

    override suspend fun setPreferences(key: PreferencesKeys, value: Set<String>) {
        preferencesFake[key.name] = value
    }

    override suspend fun getPreferences(key: PreferencesKeys): Set<String> {
        return preferencesFake[key.name] ?: emptySet()
    }
}