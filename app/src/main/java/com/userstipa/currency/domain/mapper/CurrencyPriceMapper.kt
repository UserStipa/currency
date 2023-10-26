package com.userstipa.currency.domain.mapper

import com.userstipa.currency.data.api.CurrencyDto
import com.userstipa.currency.domain.model.CurrencyPriceDetail
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class CurrencyPriceMapper : Mapper<CurrencyDto, CurrencyPriceDetail> {

    override fun map(input: List<CurrencyDto>): List<CurrencyPriceDetail> {
        val symbols = DecimalFormatSymbols().apply { groupingSeparator = ' ' }
        val decimalFormat = DecimalFormat("#,##0.00", symbols)
        val resultList = mutableListOf<CurrencyPriceDetail>()
        input.forEach {
            resultList.add(
                CurrencyPriceDetail(
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