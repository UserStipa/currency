package com.userstipa.currency.data.websocket

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface CryptocurrencyWebSocket {

    fun open(scope: CoroutineScope, ids: String): Flow<CurrencyPriceWrapperDto>

    fun close()
}