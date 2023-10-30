package com.userstipa.currency.ui.home

import com.userstipa.currency.domain.model.CurrencyPrice
import com.userstipa.currency.domain.model.CurrencyPriceDetail
import com.userstipa.currency.domain.usecases.get_my_currencies.GetMyCurrenciesFake
import com.userstipa.currency.domain.usecases.new_currencies_prices.NewCurrenciesPricesFake
import com.userstipa.currency.testUtil.DispatcherProviderFake
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
        viewModel = HomeViewModel(getMyCurrenciesFake, newCurrenciesPricesFake, dispatcherProviderFake)
    }

    @Test
    fun `get my currencies - successful`() = runTest {
        val myCurrencies = listOf(
            CurrencyPriceDetail(
                id = "bitcoin",
                name = "Bitcoin",
                symbol = "BTC",
                priceUsd = "34 312,79",
                changePercent24Hr = "1.29",
                isPositiveChangePercent24Hr = true,
                isEnableCheckbox = true
            ),
            CurrencyPriceDetail(
                id = "ethereum",
                name = "Ethereum",
                symbol = "ETH",
                priceUsd = "12 000,00",
                changePercent24Hr = "1.43",
                isPositiveChangePercent24Hr = true,
                isEnableCheckbox = true
            )
        )
        val expectedValue = HomeUiState(
            isLoading = false,
            error = null,
            list = myCurrencies
        )

        getMyCurrenciesFake.launchResult = myCurrencies
        viewModel.fetchData()

        assertEquals(expectedValue, viewModel.uiState.value)
    }

    @Test
    fun `get my currencies - error`() = runTest {
        val exception = Exception("Test error")
        val expectedValue = HomeUiState(
            isLoading = false,
            error = "Test error",
            list = emptyList()
        )

        getMyCurrenciesFake.launchException = exception
        viewModel.fetchData()

        assertEquals(expectedValue, viewModel.uiState.value)
    }

    @Test
    fun `get new prices - successful`() = runTest {
        val myCurrenciesCurrentPrice = listOf(
            CurrencyPriceDetail(
                id = "bitcoin",
                name = "Bitcoin",
                symbol = "BTC",
                priceUsd = "10000",
                changePercent24Hr = "1.29",
                isPositiveChangePercent24Hr = true,
                isEnableCheckbox = true
            ),
            CurrencyPriceDetail(
                id = "ethereum",
                name = "Ethereum",
                symbol = "ETH",
                priceUsd = "54",
                changePercent24Hr = "1.43",
                isPositiveChangePercent24Hr = true,
                isEnableCheckbox = true
            )
        )
        val newPrices = listOf(
            CurrencyPrice(
                id = "bitcoin",
                priceUsd = "24,00"
            ),
            CurrencyPrice(
                id = "ethereum",
                priceUsd = "12 000,00"
            )
        )
        val myCurrenciesNewPrice = listOf(
            CurrencyPriceDetail(
                id = "bitcoin",
                name = "Bitcoin",
                symbol = "BTC",
                priceUsd = "24,00",
                changePercent24Hr = "1.29",
                isPositiveChangePercent24Hr = true,
                isEnableCheckbox = true
            ),
            CurrencyPriceDetail(
                id = "ethereum",
                name = "Ethereum",
                symbol = "ETH",
                priceUsd = "12 000,00",
                changePercent24Hr = "1.43",
                isPositiveChangePercent24Hr = true,
                isEnableCheckbox = true
            )
        )
        val expectedValue = HomeUiState(
            isLoading = false,
            error = null,
            list = myCurrenciesNewPrice
        )

        getMyCurrenciesFake.launchResult = myCurrenciesCurrentPrice
        viewModel.fetchData()
        newCurrenciesPricesFake.subscribeResult = newPrices
        viewModel.subscribeNewPrices()


        assertEquals("New currencies", expectedValue, viewModel.uiState.value)
    }

    @Test
    fun `get new prices - error`() = runTest {
        val myCurrenciesCurrentPrice = listOf(
            CurrencyPriceDetail(
                id = "bitcoin",
                name = "Bitcoin",
                symbol = "BTC",
                priceUsd = "10000",
                changePercent24Hr = "1.29",
                isPositiveChangePercent24Hr = true,
                isEnableCheckbox = true
            ),
            CurrencyPriceDetail(
                id = "ethereum",
                name = "Ethereum",
                symbol = "ETH",
                priceUsd = "54",
                changePercent24Hr = "1.43",
                isPositiveChangePercent24Hr = true,
                isEnableCheckbox = true
            )
        )
        val exception = Exception("Test error")
        val expectedValue = HomeUiState(
            isLoading = false,
            error = exception.message,
            list = myCurrenciesCurrentPrice
        )

        getMyCurrenciesFake.launchResult = myCurrenciesCurrentPrice
        viewModel.fetchData()
        newCurrenciesPricesFake.subscribeError = exception
        viewModel.subscribeNewPrices()


        assertEquals("New currencies", expectedValue, viewModel.uiState.value)
    }
}