package com.userstipa.currency.domain.mapper

import com.userstipa.currency.data.api.CurrencyDto
import com.userstipa.currency.domain.model.Currency

class CurrencyMapper : Mapper<CurrencyDto, Currency> {

    override fun map(input: CurrencyDto): Currency {
        return Currency(
            name = input.name,
            symbol = input.symbol
        )
    }

    override fun map(input: List<CurrencyDto>): List<Currency> {
        val resultList = mutableListOf<Currency>()
        input.forEach { resultList.add(Currency(it.name, it.symbol)) }
        return resultList
    }


}