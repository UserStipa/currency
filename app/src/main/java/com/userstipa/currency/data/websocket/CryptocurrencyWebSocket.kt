package com.userstipa.currency.data.websocket

import kotlinx.coroutines.flow.Flow

interface CryptocurrencyWebSocket {

    fun open(ids: String): Flow<CurrencyPriceWrapperDto>

}