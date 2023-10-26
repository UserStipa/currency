package com.userstipa.currency.data.websocket

import com.google.gson.Gson
import com.userstipa.currency.di.dispatchers.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Inject

class CryptocurrencyWebSocketImpl @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val gson: Gson,
    private val dispatcher: DispatcherProvider
) : CryptocurrencyWebSocket {

    private var webSocket: WebSocket? = null
    private val incoming = MutableSharedFlow<CurrencyPriceWrapperDto>()

    override fun open(scope: CoroutineScope, ids: String): Flow<CurrencyPriceWrapperDto> {
        webSocket = okHttpClient.newWebSocket(createRequest(ids), Listener(scope))
        return incoming
    }

    override fun close() {
        webSocket?.close(1000, null)
    }

    inner class Listener(private val scope: CoroutineScope) : WebSocketListener() {
        override fun onMessage(webSocket: WebSocket, text: String) {
            scope.launch(dispatcher.io) {
                val result = gson.fromJson(text, CurrencyPriceWrapperDto::class.java)
                incoming.emit(result)
            }
        }
    }

    private fun createRequest(ids: String): Request {
        return Request.Builder()
            .url("wss://ws.coincap.io/prices?assets=${ids}")
            .build()
    }


}