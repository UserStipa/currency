package com.userstipa.currency.domain.mapper

import com.userstipa.currency.data.websocket.CurrencyPriceDto
import com.userstipa.currency.domain.model.CurrencyPrice

class MapperCurrencyPrice : MapperBase<CurrencyPriceDto, CurrencyPrice>() {

    override fun map(input: List<CurrencyPriceDto>): List<CurrencyPrice> {
        val resultList = mutableListOf<CurrencyPrice>()
        input.forEach {
            resultList.add(
                CurrencyPrice(
                    id = it.id,
                    priceUsd = formatPriceUsd(it.priceUsd)
                )
            )
        }
        return resultList
    }
}