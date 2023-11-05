package com.userstipa.currency.di.domain_modules

import com.userstipa.currency.data.api.CurrencyDto
import com.userstipa.currency.data.websocket.CurrencyPriceDto
import com.userstipa.currency.domain.mapper.MapperCurrency
import com.userstipa.currency.domain.mapper.MapperCurrencyPrice
import com.userstipa.currency.domain.mapper.MapperPrice
import com.userstipa.currency.domain.mapper.Mapper
import com.userstipa.currency.domain.model.Currency
import com.userstipa.currency.domain.model.Price
import com.userstipa.currency.domain.model.CurrencyPrice
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
    fun provideMapperCurrencyPriceDetail(): Mapper<CurrencyDto, CurrencyPrice> {
        return MapperCurrencyPrice()
    }

    @Provides
    @Singleton
    fun provideCurrencyPriceMapper(): Mapper<CurrencyPriceDto, Price> {
        return MapperPrice()
    }

}