package com.userstipa.currency.di

import com.userstipa.currency.data.api.CryptocurrencyApi
import com.userstipa.currency.data.api.CurrencyDto
import com.userstipa.currency.data.local.Preferences
import com.userstipa.currency.data.repository.Repository
import com.userstipa.currency.data.repository.RepositoryImpl
import com.userstipa.currency.domain.mapper.CurrencyMapper
import com.userstipa.currency.domain.mapper.Mapper
import com.userstipa.currency.domain.model.Currency
import com.userstipa.currency.domain.usecases.add_currency.AddCurrency
import com.userstipa.currency.domain.usecases.add_currency.AddCurrencyImpl
import com.userstipa.currency.domain.usecases.get_remote_currencies.GetRemoteCurrencies
import com.userstipa.currency.domain.usecases.get_remote_currencies.GetRemoteCurrenciesImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Provides
    @Singleton
    fun providerRepository(api: CryptocurrencyApi, preferences: Preferences): Repository {
        return RepositoryImpl(api, preferences)
    }

    @Provides
    @Singleton
    fun provideGetRemoteCurrencies(repository: Repository, mapper: Mapper<CurrencyDto, Currency>): GetRemoteCurrencies {
        return GetRemoteCurrenciesImpl(repository, mapper)
    }

    @Provides
    @Singleton
    fun provideAddCurrency(repository: Repository): AddCurrency {
        return AddCurrencyImpl(repository)
    }

    @Provides
    @Singleton
    fun provideCurrencyMapper(): Mapper<CurrencyDto, Currency> {
        return CurrencyMapper()
    }
}