package com.userstipa.currency.ui.details

import com.userstipa.currency.domain.model.CurrencyPriceDetails
import com.userstipa.currency.domain.model.HistoryRange
import com.userstipa.currency.domain.usecases.get_currency_price_details.GetCurrencyPriceDetailsFake
import com.userstipa.currency.testUtil.DispatcherProviderFake
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException

class DetailsViewModelTest {

    private lateinit var viewModel: DetailsViewModel
    private lateinit var getCurrencyPriceDetailsFake: GetCurrencyPriceDetailsFake

    @Before
    fun setUp() {
        val dispatcherProviderFake = DispatcherProviderFake()
        getCurrencyPriceDetailsFake = GetCurrencyPriceDetailsFake()
        viewModel = DetailsViewModel(dispatcherProviderFake, getCurrencyPriceDetailsFake)
    }

    @Test
    fun `get currency details - successful`() = runTest {
        val currencyId = "bitcoin"
        val historyRange = HistoryRange.LAST_YEAR
        val useCaseResult = CurrencyPriceDetails(
            changePercent24Hr = "1.12",
            explorer = "https://blockchain.info/",
            id = "bitcoin",
            marketCapUsd = "669720933191.1164966118771744",
            maxSupply = "21000000.0000000000000000",
            name = "Bitcoin",
            priceUsdFormatted = "34 312,79 $",
            rank = "1",
            supply = "19518112.0000000000000000",
            symbol = "BTC",
            volumeUsd24Hr = "9567526018.0479966968956894",
            vwap24Hr = "34043.8912981619835247",
            maxPriceUsdFormatted = "",
            minPriceUsdFormatted = "",
            isPositiveChangePercent24Hr = true,
            history = emptyList()
        )
        val expectedValue = DetailsUiState(
            isLoading = false,
            error = null,
            currency = useCaseResult
        )

        viewModel.fetchData(currencyId, historyRange)
        getCurrencyPriceDetailsFake.emit(useCaseResult)

        assertEquals(expectedValue, viewModel.uiState.value)
    }

    @Test
    fun `get currency details - lost connection`() = runTest {
        val currencyId = "bitcoin"
        val historyRange = HistoryRange.LAST_YEAR
        val useCaseException = IOException("Test error")
        val expectedValue = DetailsUiState(
            isLoading = false,
            error = "Lost internet connection",
            currency = null
        )

        getCurrencyPriceDetailsFake.error = useCaseException
        viewModel.fetchData(currencyId, historyRange)

        assertEquals(expectedValue, viewModel.uiState.value)
    }

    @Test
    fun `get currency details - lost exception`() = runTest {
        val currencyId = "bitcoin"
        val historyRange = HistoryRange.LAST_YEAR
        val useCaseException = Throwable("Test error")
        val expectedValue = DetailsUiState(
            isLoading = false,
            error = "Test error",
            currency = null
        )

        getCurrencyPriceDetailsFake.error = useCaseException
        viewModel.fetchData(currencyId, historyRange)

        assertEquals(expectedValue, viewModel.uiState.value)
    }
}