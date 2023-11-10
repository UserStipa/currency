package com.userstipa.currency.domain.usecases.subscribe_my_currencies

import com.userstipa.currency.domain.model.CurrencyPrice
import com.userstipa.currency.domain.model.Price
import com.userstipa.currency.domain.usecases.get_my_currencies.GetMyCurrenciesFake
import com.userstipa.currency.domain.usecases.new_currencies_prices.NewCurrenciesPricesFake
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SubscribeMyCurrenciesTest {

    private lateinit var getMyCurrenciesFake: GetMyCurrenciesFake
    private lateinit var newCurrenciesPricesFake: NewCurrenciesPricesFake
    private lateinit var subscribeMyCurrencies: SubscribeMyCurrenciesImpl

    @Before
    fun setUp() {
        getMyCurrenciesFake = GetMyCurrenciesFake()
        newCurrenciesPricesFake = NewCurrenciesPricesFake()
        subscribeMyCurrencies =
            SubscribeMyCurrenciesImpl(getMyCurrenciesFake, newCurrenciesPricesFake)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `launch - successful`() = runTest {
        //In this test subscribeMyCurrencies emits three values.
        // First - init myCurrencies, second and third with new prices

        val myCurrencies = listOf(
            CurrencyPrice(
                id = "bitcoin",
                name = "Bitcoin",
                symbol = "BTC",
                priceUsdFormatted = "34 312,79",
                changePercent24Hr = "1.29",
                isPositiveChangePercent24Hr = true,
                isEnableCheckbox = true
            ),
            CurrencyPrice(
                id = "ethereum",
                name = "Ethereum",
                symbol = "ETH",
                priceUsdFormatted = "12 000,00",
                changePercent24Hr = "1.43",
                isPositiveChangePercent24Hr = true,
                isEnableCheckbox = true
            )
        )
        val newPrices = listOf(
            listOf(
                Price(
                    id = "bitcoin",
                    priceUsdFormatted = "35 000,11"
                ),
                Price(
                    id = "ethereum",
                    priceUsdFormatted = "11 968,99"
                )
            ),
            listOf(
                Price(
                    id = "ethereum",
                    priceUsdFormatted = "11 542,32"
                )
            ),
        )
        val expectedValues = listOf(
            listOf(
                CurrencyPrice(
                    id = "bitcoin",
                    name = "Bitcoin",
                    symbol = "BTC",
                    priceUsdFormatted = "34 312,79",
                    changePercent24Hr = "1.29",
                    isPositiveChangePercent24Hr = true,
                    isEnableCheckbox = true
                ),
                CurrencyPrice(
                    id = "ethereum",
                    name = "Ethereum",
                    symbol = "ETH",
                    priceUsdFormatted = "12 000,00",
                    changePercent24Hr = "1.43",
                    isPositiveChangePercent24Hr = true,
                    isEnableCheckbox = true
                )
            ),
            listOf(
                CurrencyPrice(
                    id = "bitcoin",
                    name = "Bitcoin",
                    symbol = "BTC",
                    priceUsdFormatted = "35 000,11",
                    changePercent24Hr = "1.29",
                    isPositiveChangePercent24Hr = true,
                    isEnableCheckbox = true
                ),
                CurrencyPrice(
                    id = "ethereum",
                    name = "Ethereum",
                    symbol = "ETH",
                    priceUsdFormatted = "11 968,99",
                    changePercent24Hr = "1.43",
                    isPositiveChangePercent24Hr = true,
                    isEnableCheckbox = true
                )
            ),
            listOf(
                CurrencyPrice(
                    id = "bitcoin",
                    name = "Bitcoin",
                    symbol = "BTC",
                    priceUsdFormatted = "35 000,11",
                    changePercent24Hr = "1.29",
                    isPositiveChangePercent24Hr = true,
                    isEnableCheckbox = true
                ),
                CurrencyPrice(
                    id = "ethereum",
                    name = "Ethereum",
                    symbol = "ETH",
                    priceUsdFormatted = "11 542,32",
                    changePercent24Hr = "1.43",
                    isPositiveChangePercent24Hr = true,
                    isEnableCheckbox = true
                )
            )
        )


        val results = mutableListOf<List<CurrencyPrice>>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            subscribeMyCurrencies.launch().toList(results)
        }

        getMyCurrenciesFake.emit(myCurrencies)
        assertEquals(expectedValues[0], results[0])

        newCurrenciesPricesFake.emit(newPrices[0])
        assertEquals(expectedValues[1], results[1])

        newCurrenciesPricesFake.emit(newPrices[1])
        assertEquals(expectedValues[2], results[2])
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `launch - empty myCurrencies`() = runTest {
        val myCurrencies = listOf<CurrencyPrice>()
        val newPrices = listOf(
            Price(
                id = "bitcoin",
                priceUsdFormatted = "35 000,11"
            ),
            Price(
                id = "ethereum",
                priceUsdFormatted = "11 968,99"
            )
        )
        val expectedValue = listOf<CurrencyPrice>()

        val results = mutableListOf<List<CurrencyPrice>>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            subscribeMyCurrencies.launch().toList(results)
        }

        getMyCurrenciesFake.emit(myCurrencies)
        assertEquals(expectedValue, results[0])

        newCurrenciesPricesFake.emit(newPrices)
        assertEquals(expectedValue, results[1])
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `launch - empty newPrices`() = runTest {
        val myCurrencies = listOf(
            CurrencyPrice(
                id = "bitcoin",
                name = "Bitcoin",
                symbol = "BTC",
                priceUsdFormatted = "34 312,79",
                changePercent24Hr = "1.29",
                isPositiveChangePercent24Hr = true,
                isEnableCheckbox = true
            ),
            CurrencyPrice(
                id = "ethereum",
                name = "Ethereum",
                symbol = "ETH",
                priceUsdFormatted = "12 000,00",
                changePercent24Hr = "1.43",
                isPositiveChangePercent24Hr = true,
                isEnableCheckbox = true
            )
        )
        val newPrices = listOf<Price>()
        val expectedValue = listOf(
            CurrencyPrice(
                id = "bitcoin",
                name = "Bitcoin",
                symbol = "BTC",
                priceUsdFormatted = "34 312,79",
                changePercent24Hr = "1.29",
                isPositiveChangePercent24Hr = true,
                isEnableCheckbox = true
            ),
            CurrencyPrice(
                id = "ethereum",
                name = "Ethereum",
                symbol = "ETH",
                priceUsdFormatted = "12 000,00",
                changePercent24Hr = "1.43",
                isPositiveChangePercent24Hr = true,
                isEnableCheckbox = true
            )
        )

        val results = mutableListOf<List<CurrencyPrice>>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            subscribeMyCurrencies.launch().toList(results)
        }

        getMyCurrenciesFake.emit(myCurrencies)
        assertEquals(expectedValue, results[0])

        newCurrenciesPricesFake.emit(newPrices)
        assertEquals(expectedValue, results[1])
    }

    @Test
    fun `launch - exception in getMyCurrencies`() {
        val expectedException = Exception("Test error")
        getMyCurrenciesFake.subscribeError = expectedException

        subscribeMyCurrencies.launch().catch { actualException ->
            assertEquals(expectedException, actualException)
        }
    }

    @Test
    fun `launch - exception in newCurrenciesPrices`() {
        val expectedException = Exception("Test error")
        newCurrenciesPricesFake.subscribeError = expectedException

        subscribeMyCurrencies.launch().catch { actualException ->
            assertEquals(expectedException, actualException)
        }
    }
}