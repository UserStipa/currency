package com.userstipa.currency.di

import android.content.Context
import com.userstipa.currency.data.api.CryptocurrencyApi
import com.userstipa.currency.data.local.Preferences
import com.userstipa.currency.data.local.PreferencesImpl
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

    @Singleton
    @Provides
    fun providePreferences(context: Context): Preferences {
        return PreferencesImpl(context)
    }

}