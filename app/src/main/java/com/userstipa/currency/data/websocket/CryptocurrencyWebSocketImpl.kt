package com.userstipa.currency.data.websocket

import com.google.gson.Gson
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Inject

class CryptocurrencyWebSocketImpl @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val gson: Gson,
) : CryptocurrencyWebSocket {

    override fun open(ids: String): Flow<CurrencyPriceWrapperDto> =
        callbackFlow {
            val listener = createWebSocketListener(this)
            val request = createRequest(ids)
            val webSocket = okHttpClient.newWebSocket(request, listener)

            awaitClose {
                webSocket.close(1000, null)
            }
        }

    private fun createWebSocketListener(flow: SendChannel<CurrencyPriceWrapperDto>): WebSocketListener {
        return object : WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                val result = gson.fromJson(text, CurrencyPriceWrapperDto::class.java)
                flow.trySend(result)
            }
        }
    }

    private fun createRequest(ids: String): Request {
        return Request.Builder()
            .url("wss://ws.coincap.io/prices?assets=$ids")
            .build()
    }

}