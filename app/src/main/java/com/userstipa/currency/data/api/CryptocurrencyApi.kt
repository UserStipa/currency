package com.userstipa.currency.data.api

import retrofit2.Response
import retrofit2.http.GET

interface CryptocurrencyApi {

    @GET("v2/assets")
    suspend fun getCurrencies(): Response<GetCurrenciesDto>


}