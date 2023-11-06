package com.userstipa.currency.domain.usecases.subscribe_my_currencies

import com.userstipa.currency.domain.model.Price
import com.userstipa.currency.domain.model.CurrencyPrice
import com.userstipa.currency.domain.usecases.get_my_currencies.GetMyCurrencies
import com.userstipa.currency.domain.usecases.new_currencies_prices.NewCurrenciesPrices
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class SubscribeMyCurrenciesImpl @Inject constructor(
    private val getMyCurrencies: GetMyCurrencies,
    private val newCurrenciesPrices: NewCurrenciesPrices
) : SubscribeMyCurrencies {

    private var myCurrencies: List<CurrencyPrice> = emptyList()

    override fun launch(): Flow<List<CurrencyPrice>> = flow {
        myCurrencies = getMyCurrencies.launch().first()
        emit(myCurrencies)

        newCurrenciesPrices.subscribe().onEach { newPrices ->
            val updatedCurrencies = myCurrencies.updatePrices(newPrices)
            emit(updatedCurrencies)
            myCurrencies = updatedCurrencies
        }.collect()
    }

    private fun List<CurrencyPrice>.updatePrices(newPrices: List<Price>): List<CurrencyPrice> {
        return map { currency ->
            val price = newPrices.find { it.id == currency.id }
            if (price == null) {
                currency
            } else {
                currency.copy(priceUsdFormatted = price.priceUsdFormatted)
            }
        }
    }

}