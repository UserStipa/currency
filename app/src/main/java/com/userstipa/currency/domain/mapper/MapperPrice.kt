package com.userstipa.currency.domain.mapper

import com.userstipa.currency.data.websocket.CurrencyPriceDto
import com.userstipa.currency.domain.model.Price

class MapperPrice : MapperBase<CurrencyPriceDto, Price>() {

    override fun map(input: List<CurrencyPriceDto>): List<Price> {
        val resultList = mutableListOf<Price>()
        input.forEach {
            resultList.add(
                Price(
                    id = it.id,
                    priceUsdFormatted = formatPriceUsd(it.priceUsd),
                )
            )
        }
        return resultList
    }
}