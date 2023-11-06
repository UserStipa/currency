package com.userstipa.currency.domain.mapper

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import kotlin.math.abs

abstract class MapperBase<T, K>: Mapper<T, K> {

    private val symbols = DecimalFormatSymbols().apply { groupingSeparator = ' ' }
    private val decimalFormat = DecimalFormat("#,##0.00", symbols)

    protected fun formatPriceUsd(double: Double): String {
        return "${decimalFormat.format(double)} $"
    }

    protected fun formatChangePercent24Hr(double: Double): String {
        return "${abs(BigDecimal(double).setScale(2, RoundingMode.UP).toDouble())}"
    }

    protected fun formatDate(string: String): String {
        return string
    }

    abstract override fun map(input: List<T>): List<K>


}