package com.userstipa.currency.domain.mapper

import com.userstipa.currency.data.api.CurrencyDto
import com.userstipa.currency.domain.model.CurrencyPrice

class CurrencyPriceMapper : Mapper<CurrencyDto, CurrencyPrice> {

    override fun map(input: List<CurrencyDto>): List<CurrencyPrice> {
        val resultList = mutableListOf<CurrencyPrice>()
        input.forEach {
            resultList.add(
                CurrencyPrice(
                    id = it.id,
                    name = it.name,
                    symbol = it.symbol,
                    isEnableCheckbox = false,
                    priceUsd = it.priceUsd
                )
            )
        }
        return resultList
    }

}