package com.userstipa.currency.data.api

import com.userstipa.currency.data.api.get_currencies.WrapperCurrenciesDto
import com.userstipa.currency.data.api.get_currency_history.WrapperPriceTimeDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CryptocurrencyApi {

    @GET("v2/assets")
    suspend fun getCurrencies(): Response<WrapperCurrenciesDto>

    @GET("v2/assets")
    suspend fun getCurrencies(@Query("ids") ids: String): Response<WrapperCurrenciesDto>

    @GET("v2/assets/{id}/history")
    suspend fun getCurrencyHistory(
        @Path("id") id: String,
        @Query("interval") interval: String
    ): Response<WrapperPriceTimeDto>


}