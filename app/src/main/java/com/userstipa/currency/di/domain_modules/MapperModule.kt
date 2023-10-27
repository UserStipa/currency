package com.userstipa.currency.di.domain_modules

import com.userstipa.currency.data.api.CurrencyDto
import com.userstipa.currency.data.websocket.CurrencyPriceDto
import com.userstipa.currency.domain.mapper.MapperCurrency
import com.userstipa.currency.domain.mapper.MapperCurrencyPriceDetail
import com.userstipa.currency.domain.mapper.MapperCurrencyPrice
import com.userstipa.currency.domain.mapper.Mapper
import com.userstipa.currency.domain.model.Currency
import com.userstipa.currency.domain.model.CurrencyPrice
import com.userstipa.currency.domain.model.CurrencyPriceDetail
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
    fun provideMapperCurrencyPriceDetail(): Mapper<CurrencyDto, CurrencyPriceDetail> {
        return MapperCurrencyPriceDetail()
    }

    @Provides
    @Singleton
    fun provideCurrencyPriceMapper(): Mapper<CurrencyPriceDto, CurrencyPrice> {
        return MapperCurrencyPrice()
    }

}