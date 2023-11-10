package com.userstipa.currency.domain.usecases.new_currencies_prices

import com.userstipa.currency.data.local.PreferencesKeys
import com.userstipa.currency.data.websocket.CurrencyPriceDto
import com.userstipa.currency.data.websocket.CurrencyPriceWrapperDto
import com.userstipa.currency.domain.mapper.MapperPrice
import com.userstipa.currency.domain.model.Price
import com.userstipa.currency.domain.repository.RepositoryFake
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class NewCurrenciesPricesTest {

    private lateinit var newCurrenciesPricesImpl: NewCurrenciesPricesImpl
    private lateinit var repositoryFake: RepositoryFake

    @Before
    fun setUp() {
        repositoryFake = RepositoryFake()
        newCurrenciesPricesImpl = NewCurrenciesPricesImpl(repositoryFake, MapperPrice())
    }

    @Test
    fun `receive new prices by websocket - successful`() = runTest {
        val myCurrenciesIds = setOf("bitcoin", "ethereum")
        val newPrices = CurrencyPriceWrapperDto(
            data = listOf(
                CurrencyPriceDto(id = "bitcoin", priceUsd = 24.0),
                CurrencyPriceDto(id = "ethereum", priceUsd = 1200.0903424)
            )
        )
        val expectedNewPrices = listOf(
            Price(id = "bitcoin", priceUsdFormatted = "24,00 $"),
            Price(id = "ethereum", priceUsdFormatted = "1 200,09 $"),
        )

        repositoryFake.setPreferences(PreferencesKeys.MY_CURRENCIES, myCurrenciesIds)
        repositoryFake.openWebSocketResult = newPrices
        val actualValue = newCurrenciesPricesImpl.subscribe().first()

        Assert.assertEquals(expectedNewPrices, actualValue)
    }

    @Test
    fun `receive new prices by websocket - error`() = runTest {
        val myCurrenciesIds = setOf("bitcoin", "ethereum")
        val expectedValue = Exception("Test error")

        repositoryFake.setPreferences(PreferencesKeys.MY_CURRENCIES, myCurrenciesIds)
        repositoryFake.openWebSocketException = expectedValue

        newCurrenciesPricesImpl.subscribe().catch { actualException ->
            Assert.assertEquals(expectedValue, actualException)
        }
    }

    @Test
    fun `websocket is close when my ids is empty`() = runTest {
        val myCurrenciesIds = setOf<String>()
        val expectedEmptyNewPrices = emptyList<Price>()

        repositoryFake.setPreferences(PreferencesKeys.MY_CURRENCIES, myCurrenciesIds)
        val actualValue = newCurrenciesPricesImpl.subscribe().first()

        Assert.assertEquals(expectedEmptyNewPrices, actualValue)
    }
}