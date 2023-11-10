package com.userstipa.currency.ui.home

import com.userstipa.currency.domain.model.CurrencyPrice
import com.userstipa.currency.domain.usecases.subscribe_my_currencies.SubscribeMyCurrenciesFake
import com.userstipa.currency.testUtil.DispatcherProviderFake
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException

class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel
    private lateinit var subscribeMyCurrenciesFake: SubscribeMyCurrenciesFake
    private lateinit var dispatcherProviderFake: DispatcherProviderFake

    @Before
    fun setUp() {
        dispatcherProviderFake = DispatcherProviderFake()
        subscribeMyCurrenciesFake = SubscribeMyCurrenciesFake()
        viewModel = HomeViewModel(subscribeMyCurrenciesFake, dispatcherProviderFake)
    }

    @Test
    fun `subscribe to my currencies - successful`() = runTest {
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
        val expectedValue = HomeUiState(
            isLoading = false,
            isLoadingComplete = true,
            error = null,
            list = myCurrencies
        )

        viewModel.subscribeData()
        subscribeMyCurrenciesFake.emit(myCurrencies)

        assertEquals(expectedValue, viewModel.uiState.value)
    }

    @Test
    fun `subscribe to my currencies - lost connection`() {
        val expectedValue = HomeUiState(
            isLoading = false,
            isLoadingComplete = true,
            error = "Lost internet connection",
            list = emptyList()
        )

        subscribeMyCurrenciesFake.launchException = IOException()
        viewModel.subscribeData()

        assertEquals(expectedValue, viewModel.uiState.value)
    }

    @Test
    fun `subscribe to my currencies - exception`() {
        val expectedValue = HomeUiState(
            isLoading = false,
            isLoadingComplete = true,
            error = "Test error",
            list = emptyList()
        )

        subscribeMyCurrenciesFake.launchException = Exception("Test error")
        viewModel.subscribeData()

        assertEquals(expectedValue, viewModel.uiState.value)
    }

    @Test
    fun `unsubscribe my currencies`() = runTest {
        val myCurrencies = listOf(
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
                    priceUsdFormatted = "21 200,10",
                    changePercent24Hr = "1.29",
                    isPositiveChangePercent24Hr = true,
                    isEnableCheckbox = true
                ),
                CurrencyPrice(
                    id = "ethereum",
                    name = "Ethereum",
                    symbol = "ETH",
                    priceUsdFormatted = "14 543,32",
                    changePercent24Hr = "1.43",
                    isPositiveChangePercent24Hr = true,
                    isEnableCheckbox = true
                )
            )
        )

        viewModel.subscribeData()
        subscribeMyCurrenciesFake.emit(myCurrencies[0])

        val expectedValueBeforeUnsubscribe = HomeUiState(
            isLoading = false,
            isLoadingComplete = true,
            error = null,
            list = myCurrencies[0]
        )
        assertEquals(expectedValueBeforeUnsubscribe, viewModel.uiState.value)

        viewModel.unsubscribeData()
        subscribeMyCurrenciesFake.emit(myCurrencies[1])

        val expectedValueAfterUnsubscribe = HomeUiState(
            isLoading = false,
            isLoadingComplete = true,
            error = null,
            list = myCurrencies[0]
        )
        assertEquals(expectedValueAfterUnsubscribe, viewModel.uiState.value)
    }
}