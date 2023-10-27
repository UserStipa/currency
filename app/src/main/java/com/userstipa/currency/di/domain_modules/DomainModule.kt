package com.userstipa.currency.di.domain_modules

import com.userstipa.currency.data.api.CryptocurrencyApi
import com.userstipa.currency.data.api.CurrencyDto
import com.userstipa.currency.data.local.Preferences
import com.userstipa.currency.data.repository.Repository
import com.userstipa.currency.data.repository.RepositoryImpl
import com.userstipa.currency.data.websocket.CryptocurrencyWebSocket
import com.userstipa.currency.data.websocket.CurrencyPriceDto
import com.userstipa.currency.domain.mapper.Mapper
import com.userstipa.currency.domain.model.Currency
import com.userstipa.currency.domain.model.CurrencyPrice
import com.userstipa.currency.domain.model.CurrencyPriceDetail
import com.userstipa.currency.domain.usecases.add_currency.AddCurrency
import com.userstipa.currency.domain.usecases.add_currency.AddCurrencyImpl
import com.userstipa.currency.domain.usecases.get_all_currencies.GetAllCurrencies
import com.userstipa.currency.domain.usecases.get_all_currencies.GetAllCurrenciesImpl
import com.userstipa.currency.domain.usecases.get_my_currencies.GetMyCurrencies
import com.userstipa.currency.domain.usecases.get_my_currencies.GetMyCurrenciesImpl
import com.userstipa.currency.domain.usecases.new_currencies_prices.NewCurrenciesPrices
import com.userstipa.currency.domain.usecases.new_currencies_prices.NewCurrenciesPricesImpl
import com.userstipa.currency.domain.usecases.remove_currency.RemoveCurrency
import com.userstipa.currency.domain.usecases.remove_currency.RemoveCurrencyImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Provides
    @Singleton
    fun providerRepository(
        api: CryptocurrencyApi,
        webSocket: CryptocurrencyWebSocket,
        preferences: Preferences
    ): Repository {
        return RepositoryImpl(api, webSocket, preferences)
    }

    @Provides
    @Singleton
    fun provideGetRemoteCurrencies(
        repository: Repository,
        mapper: Mapper<CurrencyDto, Currency>
    ): GetAllCurrencies {
        return GetAllCurrenciesImpl(repository, mapper)
    }

    @Provides
    @Singleton
    fun provideRemoveCurrency(repository: Repository): RemoveCurrency {
        return RemoveCurrencyImpl(repository)
    }

    @Provides
    @Singleton
    fun provideAddCurrency(repository: Repository): AddCurrency {
        return AddCurrencyImpl(repository)
    }

    @Provides
    @Singleton
    fun provideGetMyCurrencies(
        repository: Repository,
        mapper: Mapper<CurrencyDto, CurrencyPriceDetail>
    ): GetMyCurrencies {
        return GetMyCurrenciesImpl(repository, mapper)
    }

    @Provides
    @Singleton
    fun provideGetNewCurrencies(
        repository: Repository,
        mapper: Mapper<CurrencyPriceDto, CurrencyPrice>
    ): NewCurrenciesPrices {
        return NewCurrenciesPricesImpl(repository, mapper)
    }
}