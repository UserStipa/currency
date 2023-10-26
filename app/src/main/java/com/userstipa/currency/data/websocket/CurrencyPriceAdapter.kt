package com.userstipa.currency.data.websocket

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class CurrencyPriceAdapter : JsonDeserializer<CurrencyPriceWrapperDto> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?,
    ): CurrencyPriceWrapperDto {
        val jsonObj = json.asJsonObject
        val result = mutableListOf<CurrencyPriceDto>()
        jsonObj.let { obj ->
            obj.entrySet().map { result.add(
                CurrencyPriceDto(it.key, it.value.asDouble)
            ) }
        }
        return CurrencyPriceWrapperDto(result)
    }
}