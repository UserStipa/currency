package com.userstipa.currency.domain.mapper

import com.userstipa.currency.data.api.get_currencies.CurrencyDto
import com.userstipa.currency.domain.model.CurrencyPrice

class MapperCurrencyPrice : MapperBase<CurrencyDto, CurrencyPrice>() {

    override fun map(input: List<CurrencyDto>): List<CurrencyPrice> {
        val resultList = mutableListOf<CurrencyPrice>()
        input.forEach {
            resultList.add(
                CurrencyPrice(
                    id = it.id,
                    name = it.name,
                    symbol = it.symbol,
                    priceUsdFormatted = formatPriceUsd(it.priceUsd),
                    changePercent24Hr = formatChangePercent24Hr(it.changePercent24Hr),
                    isPositiveChangePercent24Hr = (it.changePercent24Hr >= 0),
                    isEnableCheckbox = false,
                )
            )
        }
        return resultList
    }

}