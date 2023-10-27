package com.userstipa.currency.domain.usecases.new_currencies_prices

import com.userstipa.currency.data.websocket.CurrencyPriceDto
import com.userstipa.currency.data.websocket.CurrencyPriceWrapperDto
import com.userstipa.currency.domain.mapper.MapperCurrencyPrice
import com.userstipa.currency.domain.model.CurrencyPrice
import com.userstipa.currency.domain.repository.RepositoryFake
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
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
        newCurrenciesPricesImpl = NewCurrenciesPricesImpl(repositoryFake, MapperCurrencyPrice())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun subscribe() = runTest {
        val results = mutableListOf<List<CurrencyPrice>>()
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            newCurrenciesPricesImpl.subscribe(this).toList(results)
        }
        repositoryFake.webSocketFlow.emit(
            CurrencyPriceWrapperDto(
                data = listOf(
                    CurrencyPriceDto(id = "bitcoin", priceUsd = 24.0),
                    CurrencyPriceDto(id = "ethereum", priceUsd = 1200.0903424)
                )
            )
        )
        repositoryFake.webSocketFlow.emit(
            CurrencyPriceWrapperDto(
                data = listOf(CurrencyPriceDto(id = "bitcoin", priceUsd = 30.0))
            )
        )
        repositoryFake.webSocketFlow.emit(
            CurrencyPriceWrapperDto(
                data = listOf(
                    CurrencyPriceDto(id = "bitcoin", priceUsd = 20.0),
                    CurrencyPriceDto(id = "ethereum", priceUsd = 1200.1111)
                )
            )
        )
        val expectedValues = listOf(
            listOf(
                CurrencyPrice(id = "bitcoin", priceUsd = "24,00"),
                CurrencyPrice(id = "ethereum", priceUsd = "1 200,09")
            ),
            listOf(CurrencyPrice(id = "bitcoin", priceUsd = "30,00")),
            listOf(
                CurrencyPrice(id = "bitcoin", priceUsd = "20,00"),
                CurrencyPrice(id = "ethereum", priceUsd = "1 200,11")
            ),
        )
        Assert.assertEquals(expectedValues, results)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun unsubscribe() = runTest {
        val results = mutableListOf<List<CurrencyPrice>>()
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            newCurrenciesPricesImpl.subscribe(this).toList(results)
        }
        repositoryFake.webSocketFlow.emit(
            CurrencyPriceWrapperDto(
                data = listOf(
                    CurrencyPriceDto(id = "bitcoin", priceUsd = 24.0),
                    CurrencyPriceDto(id = "ethereum", priceUsd = 1200.0903424)
                )
            )
        )
        repositoryFake.webSocketFlow.emit(
            CurrencyPriceWrapperDto(
                data = listOf(CurrencyPriceDto(id = "bitcoin", priceUsd = 30.0))
            )
        )

        newCurrenciesPricesImpl.unsubscribe()


        repositoryFake.webSocketFlow.emit(
            CurrencyPriceWrapperDto(
                data = listOf(
                    CurrencyPriceDto(id = "bitcoin", priceUsd = 20.0),
                    CurrencyPriceDto(id = "ethereum", priceUsd = 1200.1111)
                )
            )
        )
        val expectedValues = listOf(
            listOf(
                CurrencyPrice(id = "bitcoin", priceUsd = "24,00"),
                CurrencyPrice(id = "ethereum", priceUsd = "1 200,09")
            ),
            listOf(CurrencyPrice(id = "bitcoin", priceUsd = "30,00"))
        )
        Assert.assertEquals(expectedValues, results)
    }
}