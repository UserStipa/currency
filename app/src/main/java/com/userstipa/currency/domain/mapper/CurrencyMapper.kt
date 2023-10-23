package com.userstipa.currency.domain.mapper

import com.userstipa.currency.data.api.CurrencyDto
import com.userstipa.currency.domain.model.Currency

class CurrencyMapper : Mapper<CurrencyDto, Currency>{

    override fun map(input: CurrencyDto): Currency {
        return Currency(input.name)
    }

    override fun map(input: List<CurrencyDto>): List<Currency> {
        val resultList = mutableListOf<Currency>()
        input.forEach { resultList.add(Currency(it.name)) }
        return resultList
    }


}