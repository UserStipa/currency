package com.userstipa.currency.domain.mapper

import com.userstipa.currency.data.api.get_currencies.CurrencyDto
import com.userstipa.currency.domain.model.CurrencyPriceDetails

class MapperCurrencyPriceDetails : MapperBase<CurrencyDto, CurrencyPriceDetails>() {

    override fun map(input: List<CurrencyDto>): List<CurrencyPriceDetails> {
        val resultList = mutableListOf<CurrencyPriceDetails>()
        input.forEach {
            resultList.add(
                CurrencyPriceDetails(
                    id = it.id,
                    name = it.name,
                    symbol = it.symbol,
                    priceUsd = formatPriceUsd(it.priceUsd),
                    changePercent24Hr = formatChangePercent24Hr(it.changePercent24Hr),
                    isPositiveChangePercent24Hr = (it.changePercent24Hr >= 0),
                    marketCapUsd = it.marketCapUsd,
                    rank = it.rank,
                    supply = it.supply,
                    volumeUsd24Hr = it.volumeUsd24Hr,
                    maxSupply = it.maxSupply ?: NULL_VALUE,
                    vwap24Hr = it.vwap24Hr ?: NULL_VALUE,
                    explorer = it.explorer ?: NULL_VALUE,
                    history = emptyList()
                )
            )
        }
        return resultList
    }

    companion object {
        private const val NULL_VALUE = "N/A"
    }
}