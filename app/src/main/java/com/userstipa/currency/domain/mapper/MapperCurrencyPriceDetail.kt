package com.userstipa.currency.domain.mapper

import com.userstipa.currency.data.api.CurrencyDto
import com.userstipa.currency.domain.model.CurrencyPriceDetail
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class MapperCurrencyPriceDetail : Mapper<CurrencyDto, CurrencyPriceDetail> {

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
                    priceUsd = decimalFormat.format(it.priceUsd),
                    changePercent24Hr = BigDecimal(it.changePercent24Hr).setScale(2, RoundingMode.UP).toString(),
                    isEnableCheckbox = false,
                )
            )
        }
        return resultList
    }

}