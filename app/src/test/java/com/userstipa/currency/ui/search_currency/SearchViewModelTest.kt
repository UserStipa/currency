package com.userstipa.currency.ui.search_currency

import com.userstipa.currency.domain.model.Currency
import com.userstipa.currency.domain.usecases.add_currency.AddCurrencyFake
import com.userstipa.currency.domain.usecases.get_all_currencies.GetAllCurrenciesFake
import com.userstipa.currency.domain.usecases.remove_currency.RemoveCurrencyFake
import com.userstipa.currency.testUtil.DispatcherProviderFake
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException

class SearchViewModelTest {

    private lateinit var viewModel: SearchViewModel
    private lateinit var getRemoteCurrencies: GetAllCurrenciesFake
    private lateinit var addCurrency: AddCurrencyFake
    private lateinit var removeCurrency: RemoveCurrencyFake

    @Before
    fun setUp() {
        val dispatcherProviderFake = DispatcherProviderFake()
        getRemoteCurrencies = GetAllCurrenciesFake()
        addCurrency = AddCurrencyFake()
        removeCurrency = RemoveCurrencyFake()
        viewModel = SearchViewModel(getRemoteCurrencies, addCurrency, removeCurrency, dispatcherProviderFake)
    }


    @Test
    fun `get all currencies - successful`() = runTest {
        val allCurrencies = listOf(
            Currency("bitcoin1", "Bitcoin_0", "BTC_0", false),
            Currency("bitcoin2", "Bitcoin_1", "BTC_1", true),
            Currency("bitcoin3", "Bitcoin_2", "BTC_2", false),
            Currency("bitcoin4", "Bitcoin_3", "BTC_3", false),
        )
        val expectedValue = SearchUiState(list = allCurrencies)

        getRemoteCurrencies.launchResult = allCurrencies
        viewModel.fetchData()

        Assert.assertEquals(expectedValue, viewModel.uiState.value)
    }

    @Test
    fun `get all currencies - lost connection`() = runTest {
        val exception = IOException()
        val expectedValue = SearchUiState(error = "Lost internet connection")

        getRemoteCurrencies.launchException = exception
        viewModel.fetchData()

        Assert.assertEquals(expectedValue, viewModel.uiState.value)
    }

    @Test
    fun `get all currencies - error`() = runTest {
        val exception = Exception("Test error")
        val expectedValue = SearchUiState(error = exception.message)

        getRemoteCurrencies.launchException = exception
        viewModel.fetchData()

        Assert.assertEquals(expectedValue, viewModel.uiState.value)
    }

    @Test
    fun `add currency`() = runTest {
        val expectedValue = Currency("bitcoin1", "Bitcoin_0", "BTC_0", false)
        viewModel.addCurrency(expectedValue)
        Assert.assertEquals(expectedValue, addCurrency.result)
    }

    @Test
    fun `remove currency`() = runTest {
        val expectedValue = Currency("bitcoin1", "Bitcoin_0", "BTC_0", true)
        viewModel.removeCurrency(expectedValue)
        Assert.assertEquals(expectedValue, removeCurrency.result)
    }
}