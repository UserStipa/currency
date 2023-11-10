package com.userstipa.currency.domain.model

enum class HistoryRange(
    val timeInterval: String
) {
    LAST_HOUR(timeInterval = "m1"),
    LAST_DAY(timeInterval = "m5"),
    LAST_WEEK(timeInterval = "h2"),
    LAST_MONTH(timeInterval = "h12"),
    LAST_YEAR(timeInterval = "d1");

    companion object {
        val DEFAULT_VALUE = LAST_DAY
    }
}