package com.userstipa.currency.domain.usecases.get_remote_currencies

import com.userstipa.currency.data.api.CurrencyDto
import com.userstipa.currency.data.api.GetCurrenciesDto
import com.userstipa.currency.domain.Resource
import com.userstipa.currency.domain.mapper.CurrencyMapper
import com.userstipa.currency.domain.model.Currency
import com.userstipa.currency.testUtil.RepositoryFake
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class GetRemoteCurrenciesImplTest {

    private lateinit var getRemoteCurrencies: GetRemoteCurrencies
    private lateinit var repositoryFake: RepositoryFake

    @Before
    fun setUp() {
        repositoryFake = RepositoryFake()
        getRemoteCurrencies = GetRemoteCurrenciesImpl(repositoryFake, CurrencyMapper())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun launch() = runTest {

        val data = listOf(
            CurrencyDto(
                changePercent24Hr = "value_changePercent24Hr",
                explorer = "value_explorer",
                id = "value_id",
                marketCapUsd = "value_marketCapUsd",
                maxSupply = "value_maxSupply",
                name = "value_name",
                priceUsd = "value_priceUsd",
                rank = "value_rank",
                supply = "value_supply",
                symbol = "value_symbol",
                volumeUsd24Hr = "value_volumeUsd24Hr",
                vwap24Hr = "value_vwap24Hr"
            )
        )

        val body = GetCurrenciesDto(
            data = data,
            timestamp = "00000"
        )

        val response = Response.success(body)
        repositoryFake.result = response

        val values = mutableListOf<Resource<List<Currency>>>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            getRemoteCurrencies.launch().toList(values)
        }

        val expectedLoading = Resource.Loading<List<Currency>>()
        val actualLoading = values[0]
        Assert.assertEquals(expectedLoading.javaClass.name, actualLoading.javaClass.name)

        val expectedValue = Resource.Success(
            listOf(
                Currency(
                    id = "value_id",
                    name = "value_name",
                    symbol = "value_symbol",
                    isEnableCheckbox = false
                )
            )
        )
        val actualValue = values[1]
        Assert.assertEquals(expectedValue, actualValue)


    }
}