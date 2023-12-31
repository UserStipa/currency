package com.userstipa.currency.di.domain_modules

import com.userstipa.currency.data.api.get_currencies.CurrencyDto
import com.userstipa.currency.data.api.get_currency_history.PriceTimeDto
import com.userstipa.currency.data.repository.Repository
import com.userstipa.currency.data.websocket.CurrencyPriceDto
import com.userstipa.currency.domain.mapper.Mapper
import com.userstipa.currency.domain.model.Currency
import com.userstipa.currency.domain.model.CurrencyPrice
import com.userstipa.currency.domain.model.CurrencyPriceDetails
import com.userstipa.currency.domain.model.Price
import com.userstipa.currency.domain.model.PriceTime
import com.userstipa.currency.domain.usecases.add_currency.AddCurrency
import com.userstipa.currency.domain.usecases.add_currency.AddCurrencyImpl
import com.userstipa.currency.domain.usecases.get_all_currencies.GetAllCurrencies
import com.userstipa.currency.domain.usecases.get_all_currencies.GetAllCurrenciesImpl
import com.userstipa.currency.domain.usecases.get_currency_price_details.FilterHistory
import com.userstipa.currency.domain.usecases.get_currency_price_details.FilterHistoryImpl
import com.userstipa.currency.domain.usecases.get_currency_price_details.GetCurrencyPriceDetails
import com.userstipa.currency.domain.usecases.get_currency_price_details.GetCurrencyPriceDetailsImpl
import com.userstipa.currency.domain.usecases.get_my_currencies.GetMyCurrencies
import com.userstipa.currency.domain.usecases.get_my_currencies.GetMyCurrenciesImpl
import com.userstipa.currency.domain.usecases.new_currencies_prices.NewCurrenciesPrices
import com.userstipa.currency.domain.usecases.new_currencies_prices.NewCurrenciesPricesImpl
import com.userstipa.currency.domain.usecases.remove_currency.RemoveCurrency
import com.userstipa.currency.domain.usecases.remove_currency.RemoveCurrencyImpl
import com.userstipa.currency.domain.usecases.subscribe_my_currencies.SubscribeMyCurrencies
import com.userstipa.currency.domain.usecases.subscribe_my_currencies.SubscribeMyCurrenciesImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

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
        mapper: Mapper<CurrencyDto, CurrencyPrice>
    ): GetMyCurrencies {
        return GetMyCurrenciesImpl(repository, mapper)
    }

    @Provides
    @Singleton
    fun provideGetNewCurrencies(
        repository: Repository,
        mapper: Mapper<CurrencyPriceDto, Price>
    ): NewCurrenciesPrices {
        return NewCurrenciesPricesImpl(repository, mapper)
    }

    @Provides
    @Singleton
    fun provideFilterHistory(): FilterHistory {
        return FilterHistoryImpl()
    }

    @Provides
    @Singleton
    fun provideGetCurrencyPriceDetails(
        repository: Repository,
        mapperCurrency: Mapper<CurrencyDto, CurrencyPriceDetails>,
        mapperPriceTime: Mapper<PriceTimeDto, PriceTime>,
        filterHistory: FilterHistory
    ) : GetCurrencyPriceDetails {
        return GetCurrencyPriceDetailsImpl(repository, mapperCurrency, mapperPriceTime, filterHistory)
    }

    @Provides
    @Singleton
    fun provideSubscribeMyCurrencies(
        getMyCurrencies: GetMyCurrencies,
        newCurrenciesPrices: NewCurrenciesPrices
    ): SubscribeMyCurrencies {
        return SubscribeMyCurrenciesImpl(getMyCurrencies, newCurrenciesPrices)
    }
}