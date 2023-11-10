package com.userstipa.currency.di.domain_modules

import com.userstipa.currency.data.api.get_currencies.CurrencyDto
import com.userstipa.currency.data.api.get_currency_history.PriceTimeDto
import com.userstipa.currency.data.websocket.CurrencyPriceDto
import com.userstipa.currency.domain.mapper.MapperCurrency
import com.userstipa.currency.domain.mapper.MapperCurrencyPrice
import com.userstipa.currency.domain.mapper.MapperPrice
import com.userstipa.currency.domain.mapper.Mapper
import com.userstipa.currency.domain.mapper.MapperCurrencyPriceDetails
import com.userstipa.currency.domain.mapper.MapperPriceTime
import com.userstipa.currency.domain.model.Currency
import com.userstipa.currency.domain.model.Price
import com.userstipa.currency.domain.model.CurrencyPrice
import com.userstipa.currency.domain.model.CurrencyPriceDetails
import com.userstipa.currency.domain.model.PriceTime
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MapperModule {

    @Provides
    @Singleton
    fun provideMapperCurrency(): Mapper<CurrencyDto, Currency> {
        return MapperCurrency()
    }

    @Provides
    @Singleton
    fun provideMapperCurrencyPrice(): Mapper<CurrencyDto, CurrencyPrice> {
        return MapperCurrencyPrice()
    }

    @Provides
    @Singleton
    fun provideMapperCurrencyPriceDetails(): Mapper<CurrencyDto, CurrencyPriceDetails> {
        return MapperCurrencyPriceDetails()
    }

    @Provides
    @Singleton
    fun provideMapperPrice(): Mapper<CurrencyPriceDto, Price> {
        return MapperPrice()
    }

    @Provides
    @Singleton
    fun provideMapperPriceTime(): Mapper<PriceTimeDto, PriceTime> {
        return MapperPriceTime()
    }

}