package com.userstipa.currency.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptocurrencyApi {

    @GET("v2/assets")
    suspend fun getCurrencies(): Response<GetCurrenciesDto>

    @GET("v2/assets")
    suspend fun getCurrencies(@Query("ids") ids: String): Response<GetCurrenciesDto>


}