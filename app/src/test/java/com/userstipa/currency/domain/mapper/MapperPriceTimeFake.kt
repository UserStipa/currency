package com.userstipa.currency.domain.mapper

import com.userstipa.currency.data.api.get_currency_history.PriceTimeDto
import com.userstipa.currency.domain.model.PriceTime
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

class MapperPriceTimeFake : MapperBase<PriceTimeDto, PriceTime>() {

    var mapException: Throwable? = null

    override fun map(input: List<PriceTimeDto>): List<PriceTime> {
        mapException?.let { throw it }
        val resultList = mutableListOf<PriceTime>()
        val zoneId = ZoneId.of("UTC")
        input.forEach {
            resultList.add(
                PriceTime(
                    priceUsd = it.priceUsd,
                    priceUsdFormatted = formatPriceUsd(it.priceUsd),
                    dateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(it.time), zoneId),
                    dateTimeFormatted = formatDate(it.date)
                )
            )
        }
        return resultList
    }
}