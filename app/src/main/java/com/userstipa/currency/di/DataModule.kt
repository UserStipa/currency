package com.userstipa.currency.di

import com.userstipa.currency.data.api.CryptocurrencyApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideCryptocurrencyApi(): CryptocurrencyApi {
        return Retrofit.Builder()
            .baseUrl("https://api.coincap.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptocurrencyApi::class.java)
    }

}