package com.userstipa.currency.domain.mapper

import com.userstipa.currency.data.api.get_currency_history.PriceTimeDto
import com.userstipa.currency.domain.model.PriceTime

class MapperPriceTime : MapperBase<PriceTimeDto, PriceTime>() {

    override fun map(input: List<PriceTimeDto>): List<PriceTime> {
        val resultList = mutableListOf<PriceTime>()
        input.forEach {
            resultList.add(
                PriceTime(
                    priceUsd = formatPriceUsd(it.priceUsd),
                    dateTime = formatDate(it.date)
                )
            )
        }
        return resultList
    }
}