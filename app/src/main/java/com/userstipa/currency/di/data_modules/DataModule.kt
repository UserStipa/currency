package com.userstipa.currency.di.data_modules

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.userstipa.currency.data.api.CryptocurrencyApi
import com.userstipa.currency.data.local.Preferences
import com.userstipa.currency.data.local.PreferencesImpl
import com.userstipa.currency.data.repository.Repository
import com.userstipa.currency.data.repository.RepositoryImpl
import com.userstipa.currency.data.websocket.CryptocurrencyWebSocket
import com.userstipa.currency.data.websocket.CryptocurrencyWebSocketImpl
import com.userstipa.currency.data.websocket.CurrencyPriceAdapter
import com.userstipa.currency.data.websocket.CurrencyPriceWrapperDto
import com.userstipa.currency.di.dispatchers.DispatcherProvider
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
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

    @Singleton
    @Provides
    fun provideCryptocurrencyWebSocket(
        gson: Gson,
        okHttpClient: OkHttpClient,
        dispatcherProvides: DispatcherProvider
    ): CryptocurrencyWebSocket {
        return CryptocurrencyWebSocketImpl(okHttpClient, gson, dispatcherProvides)
    }

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(CurrencyPriceWrapperDto::class.java, CurrencyPriceAdapter())
            .create()
    }

    @Provides
    @Singleton
    fun providerRepository(
        api: CryptocurrencyApi,
        webSocket: CryptocurrencyWebSocket,
        preferences: Preferences
    ): Repository {
        return RepositoryImpl(api, webSocket, preferences)
    }

}