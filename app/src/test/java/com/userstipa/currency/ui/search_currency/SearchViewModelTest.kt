package com.userstipa.currency.ui.search_currency

import com.userstipa.currency.domain.Resource
import com.userstipa.currency.domain.model.Currency
import com.userstipa.currency.testUtil.DispatcherProviderFake
import com.userstipa.currency.testUtil.GetRemoteCurrenciesFake
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SearchViewModelTest {

    private lateinit var viewModel: SearchViewModel
    private lateinit var getRemoteCurrencies: GetRemoteCurrenciesFake
    private lateinit var dispatcher: DispatcherProviderFake

    @Before
    fun setUp() {
        dispatcher = DispatcherProviderFake()
        getRemoteCurrencies = GetRemoteCurrenciesFake()
        viewModel = SearchViewModel(getRemoteCurrencies, dispatcher)
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
            Currency("BTC_0", "Bitcoin_0"),
            Currency("BTC_1", "Bitcoin_2"),
            Currency("BTC_2", "Bitcoin_3"),
            Currency("BTC_4", "Bitcoin_4"),
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
}