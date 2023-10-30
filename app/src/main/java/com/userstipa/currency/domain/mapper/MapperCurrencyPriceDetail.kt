package com.userstipa.currency.domain.mapper

import com.userstipa.currency.data.api.CurrencyDto
import com.userstipa.currency.domain.model.CurrencyPriceDetail

class MapperCurrencyPriceDetail : MapperBase<CurrencyDto, CurrencyPriceDetail>() {

    override fun map(input: List<CurrencyDto>): List<CurrencyPriceDetail> {
        val resultList = mutableListOf<CurrencyPriceDetail>()
        input.forEach {
            resultList.add(
                CurrencyPriceDetail(
                    id = it.id,
                    name = it.name,
                    symbol = it.symbol,
                    priceUsd = formatPriceUsd(it.priceUsd),
                    changePercent24Hr = formatChangePercent24Hr(it.changePercent24Hr),
                    isPositiveChangePercent24Hr = (it.changePercent24Hr >= 0),
                    isEnableCheckbox = false,
                )
            )
        }
        return resultList
    }

}