package com.userstipa.currency.domain.mapper

import com.userstipa.currency.data.websocket.CurrencyPriceDto
import com.userstipa.currency.domain.model.CurrencyPrice
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class CurrencyPriceMapper : Mapper<CurrencyPriceDto, CurrencyPrice> {

    override fun map(input: List<CurrencyPriceDto>): List<CurrencyPrice> {
        val symbols = DecimalFormatSymbols().apply { groupingSeparator = ' ' }
        val decimalFormat = DecimalFormat("#,##0.00", symbols)
        val resultList = mutableListOf<CurrencyPrice>()
        input.forEach {
            resultList.add(
                CurrencyPrice(
                    id = it.id,
                    priceUsd = decimalFormat.format(it.priceUsd)
                )
            )
        }
        return resultList
    }
}