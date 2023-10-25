package com.userstipa.currency.ui.search_currency

import com.userstipa.currency.domain.Resource
import com.userstipa.currency.domain.model.Currency
import com.userstipa.currency.testUtil.AddCurrencyFake
import com.userstipa.currency.testUtil.DispatcherProviderFake
import com.userstipa.currency.testUtil.GetRemoteCurrenciesFake
import com.userstipa.currency.testUtil.RemoveCurrencyFake
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SearchViewModelTest {

    private lateinit var viewModel: SearchViewModel
    private lateinit var getRemoteCurrencies: GetRemoteCurrenciesFake
    private lateinit var addCurrency: AddCurrencyFake
    private lateinit var removeCurrency: RemoveCurrencyFake
    private lateinit var dispatcher: DispatcherProviderFake

    @Before
    fun setUp() {
        dispatcher = DispatcherProviderFake()
        getRemoteCurrencies = GetRemoteCurrenciesFake()
        addCurrency = AddCurrencyFake()
        removeCurrency = RemoveCurrencyFake()
        viewModel = SearchViewModel(getRemoteCurrencies, addCurrency, removeCurrency, dispatcher)
    }

    @Test
    fun `getUiState - init state`() = runTest {
        val expectedValue = SearchUiState()
        Assert.assertEquals(expectedValue, viewModel.uiState.value)
    }

    @Test
    fun `getUiState - loading state`() = runTest {
        viewModel.fetchData()
        getRemoteCurrencies.emit(Resource.Loading())
        val expectedValue = SearchUiState(isLoading = true)
        Assert.assertEquals(expectedValue, viewModel.uiState.value)
    }

    @Test
    fun `getUiState - get list successful state`() = runTest {
        viewModel.fetchData()
        val list = listOf(
            Currency("bitcoin1", "Bitcoin_0", "BTC_0", false),
            Currency("bitcoin2", "Bitcoin_1", "BTC_1", true),
            Currency("bitcoin3", "Bitcoin_2", "BTC_2", false),
            Currency("bitcoin4", "Bitcoin_3", "BTC_3", false),
        )
        getRemoteCurrencies.emit(Resource.Success(list))

        val expectedValue = SearchUiState(list = list)
        Assert.assertEquals(expectedValue, viewModel.uiState.value)
    }

    @Test
    fun `getUiState - get exception`() = runTest {
        viewModel.fetchData()
        val exception = Exception("Test error")
        getRemoteCurrencies.emit(Resource.Error(exception))

        val expectedValue = SearchUiState(error = "Test error")
        Assert.assertEquals(expectedValue, viewModel.uiState.value)
    }

    @Test
    fun `add currency`() = runTest {
        val expectedValue = Currency("bitcoin1", "Bitcoin_0", "BTC_0", false)
        viewModel.addCurrency(expectedValue)
        Assert.assertEquals(expectedValue, addCurrency.result)
    }
}