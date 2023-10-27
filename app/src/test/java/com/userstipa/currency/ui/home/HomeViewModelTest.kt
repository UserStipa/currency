package com.userstipa.currency.ui.home

import com.userstipa.currency.domain.Resource
import com.userstipa.currency.domain.model.CurrencyPrice
import com.userstipa.currency.domain.model.CurrencyPriceDetail
import com.userstipa.currency.testUtil.DispatcherProviderFake
import com.userstipa.currency.testUtil.GetMyCurrenciesFake
import com.userstipa.currency.testUtil.NewCurrenciesPricesFake
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel
    private lateinit var getMyCurrenciesFake: GetMyCurrenciesFake
    private lateinit var newCurrenciesPricesFake: NewCurrenciesPricesFake
    private lateinit var dispatcherProviderFake: DispatcherProviderFake

    @Before
    fun setUp() {
        getMyCurrenciesFake = GetMyCurrenciesFake()
        newCurrenciesPricesFake = NewCurrenciesPricesFake()
        dispatcherProviderFake = DispatcherProviderFake()
        viewModel =
            HomeViewModel(getMyCurrenciesFake, newCurrenciesPricesFake, dispatcherProviderFake)
    }

    @Test
    fun getUiState() {
        val expectedValue = HomeUiState(
            isLoading = false,
            error = null,
            list = emptyList()
        )
        assertEquals(expectedValue, viewModel.uiState.value)
    }

    @Test
    fun `fetchData - successful`() = runTest {
        val expectedValue = Resource.Success(
            data = listOf(
                CurrencyPriceDetail(
                    id = "bitcoin",
                    name = "Bitcoin",
                    symbol = "BTC",
                    priceUsd = "34 312,79",
                    isEnableCheckbox = true
                ),
                CurrencyPriceDetail(
                    id = "ethereum",
                    name = "Ethereum",
                    symbol = "ETH",
                    priceUsd = "12 000,00",
                    isEnableCheckbox = true
                )
            )
        )

        val expectedUiState = HomeUiState(
            isLoading = false,
            error = null,
            list = expectedValue.data
        )
        viewModel.fetchData()
        getMyCurrenciesFake.emit(expectedValue)
        assertEquals(expectedUiState, viewModel.uiState.value)
    }

    @Test
    fun `fetchData - error`() = runTest {
        val expectedException = Resource.Error<List<CurrencyPriceDetail>>(Exception("Test error"))
        val expectedUiState = HomeUiState(
            isLoading = false,
            error = "Test error",
            list = emptyList()
        )
        viewModel.fetchData()
        getMyCurrenciesFake.emit(expectedException)
        assertEquals(expectedUiState, viewModel.uiState.value)
    }

    @Test
    fun subscribeNewPrices() = runTest {
        val initValues = Resource.Success(
            data = listOf(
                CurrencyPriceDetail(
                    id = "bitcoin",
                    name = "Bitcoin",
                    symbol = "BTC",
                    priceUsd = "34 312,79",
                    isEnableCheckbox = true
                ),
                CurrencyPriceDetail(
                    id = "ethereum",
                    name = "Ethereum",
                    symbol = "ETH",
                    priceUsd = "12 000,00",
                    isEnableCheckbox = true
                )
            )
        )

        val expectedUiStateInit = HomeUiState(
            isLoading = false,
            error = null,
            list = initValues.data
        )

        val newPrices = listOf(
            CurrencyPrice(id = "bitcoin", priceUsd = "24,00")
        )

        val expectedUiStateNewPrices = HomeUiState(
            isLoading = false,
            error = null,
            list = listOf(
                CurrencyPriceDetail(
                    id = "bitcoin",
                    name = "Bitcoin",
                    symbol = "BTC",
                    priceUsd = "24,00",
                    isEnableCheckbox = true
                ),
                CurrencyPriceDetail(
                    id = "ethereum",
                    name = "Ethereum",
                    symbol = "ETH",
                    priceUsd = "12 000,00",
                    isEnableCheckbox = true
                )
            )
        )

        viewModel.fetchData()
        getMyCurrenciesFake.emit(initValues)

        val actualUiStateInit = viewModel.uiState.value
        assertEquals("Init currencies", expectedUiStateInit, actualUiStateInit)

        viewModel.subscribeNewPrices()
        newCurrenciesPricesFake.webSocketFlow.emit(newPrices)

        val actualUiStateNewPrices = viewModel.uiState.value
        assertEquals("New currencies", expectedUiStateNewPrices, actualUiStateNewPrices)

    }

    @Test
    fun unsubscribeNewPrices() = runTest {
        val initValues = Resource.Success(
            data = listOf(
                CurrencyPriceDetail(
                    id = "bitcoin",
                    name = "Bitcoin",
                    symbol = "BTC",
                    priceUsd = "34 312,79",
                    isEnableCheckbox = true
                ),
                CurrencyPriceDetail(
                    id = "ethereum",
                    name = "Ethereum",
                    symbol = "ETH",
                    priceUsd = "12 000,00",
                    isEnableCheckbox = true
                )
            )
        )

        val expectedUiStateInit = HomeUiState(
            isLoading = false,
            error = null,
            list = initValues.data
        )

        val newPricesFirst = listOf(
            CurrencyPrice(id = "bitcoin", priceUsd = "24,00")
        )

        val expectedUiStateNewPrices = HomeUiState(
            isLoading = false,
            error = null,
            list = listOf(
                CurrencyPriceDetail(
                    id = "bitcoin",
                    name = "Bitcoin",
                    symbol = "BTC",
                    priceUsd = "24,00",
                    isEnableCheckbox = true
                ),
                CurrencyPriceDetail(
                    id = "ethereum",
                    name = "Ethereum",
                    symbol = "ETH",
                    priceUsd = "12 000,00",
                    isEnableCheckbox = true
                )
            )
        )

        val newPricesSecond = listOf(
            CurrencyPrice(id = "bitcoin", priceUsd = "10000,00")
        )


        viewModel.fetchData()
        getMyCurrenciesFake.emit(initValues)

        val actualUiStateInit = viewModel.uiState.value
        assertEquals("WebSocket close - init values", expectedUiStateInit, actualUiStateInit)

        viewModel.subscribeNewPrices()
        newCurrenciesPricesFake.webSocketFlow.emit(newPricesFirst)

        val actualUiStateNewPrices = viewModel.uiState.value
        assertEquals(
            "WebSocket open - new values",
            expectedUiStateNewPrices,
            actualUiStateNewPrices
        )

        viewModel.unsubscribeNewPrices()
        newCurrenciesPricesFake.webSocketFlow.emit(newPricesSecond)

        val actualUiState = viewModel.uiState.value
        assertEquals("WebSocket close - previous values", expectedUiStateNewPrices, actualUiState)
    }
}