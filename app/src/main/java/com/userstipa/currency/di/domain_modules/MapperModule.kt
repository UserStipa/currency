package com.userstipa.currency.di.domain_modules

import com.userstipa.currency.data.api.CurrencyDto
import com.userstipa.currency.data.websocket.CurrencyPriceDto
import com.userstipa.currency.domain.mapper.CurrencyMapper
import com.userstipa.currency.domain.mapper.CurrencyPriceDetailMapper
import com.userstipa.currency.domain.mapper.CurrencyPriceMapper
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
    fun provideCurrencyMapper(): Mapper<CurrencyDto, Currency> {
        return CurrencyMapper()
    }

    @Provides
    @Singleton
    fun provideCurrencyPriceDetailMapper(): Mapper<CurrencyDto, CurrencyPriceDetail> {
        return CurrencyPriceDetailMapper()
    }

    @Provides
    @Singleton
    fun provideCurrencyPriceMapper(): Mapper<CurrencyPriceDto, CurrencyPrice> {
        return CurrencyPriceMapper()
    }

}