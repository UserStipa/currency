package com.userstipa.currency.domain.model

enum class HistoryRange(
    val timeInterval: String
) {
    LAST_HOUR("m1"),
    LAST_WEEK("h2"),
    LAST_MONTH("h12"),
    LAST_YEAR("d1")
}