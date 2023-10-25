package com.userstipa.currency.domain.mapper

import com.userstipa.currency.data.api.CurrencyDto
import com.userstipa.currency.domain.model.CurrencyPrice
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class CurrencyPriceMapper : Mapper<CurrencyDto, CurrencyPrice> {

    override fun map(input: List<CurrencyDto>): List<CurrencyPrice> {
        val symbols = DecimalFormatSymbols().apply { groupingSeparator = ' ' }
        val decimalFormat = DecimalFormat("#,##0.00", symbols)
        val resultList = mutableListOf<CurrencyPrice>()
        input.forEach {
            resultList.add(
                CurrencyPrice(
                    id = it.id,
                    name = it.name,
                    symbol = it.symbol,
                    isEnableCheckbox = false,
                    priceUsd = decimalFormat.format(it.priceUsd)
                )
            )
        }
        return resultList
    }

}