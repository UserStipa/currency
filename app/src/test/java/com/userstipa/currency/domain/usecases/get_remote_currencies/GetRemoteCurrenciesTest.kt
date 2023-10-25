package com.userstipa.currency.domain.usecases.get_remote_currencies

import com.userstipa.currency.data.api.CurrencyDto
import com.userstipa.currency.data.api.GetCurrenciesDto
import com.userstipa.currency.data.local.PreferencesKeys
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

class GetRemoteCurrenciesTest {

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
        repositoryFake.setPreferences(PreferencesKeys.MY_CURRENCIES, setOf("bitcoin"))

        val data = listOf(
            CurrencyDto(
                changePercent24Hr = "-1.1119214668658208",
                explorer = "https://blockchain.info/",
                id = "bitcoin",
                marketCapUsd = "669720933191.1164966118771744",
                maxSupply = "21000000.0000000000000000",
                name = "Bitcoin",
                priceUsd = "34312.7928147515751837",
                rank = "1",
                supply = "19518112.0000000000000000",
                symbol = "BTC",
                volumeUsd24Hr = "9567526018.0479966968956894",
                vwap24Hr = "34043.8912981619835247"
            ),
            CurrencyDto(
                changePercent24Hr = "-3.3659161263923668",
                explorer = "https://etherscan.io/",
                id = "ethereum",
                marketCapUsd = "214877322599.5196305515861831",
                maxSupply = "null",
                name = "Ethereum",
                priceUsd = "1786.7197598854497559",
                rank = "2",
                supply = "120263584.3761507700000000",
                symbol = "ETH",
                volumeUsd24Hr = "4660708810.0445978384727955",
                vwap24Hr = "1793.3749382772015356"
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
                    id = "bitcoin",
                    name = "Bitcoin",
                    symbol = "BTC",
                    isEnableCheckbox = true
                ),
                Currency(
                    id = "ethereum",
                    name = "Ethereum",
                    symbol = "ETH",
                    isEnableCheckbox = false
                )
            )
        )
        val actualValue = values[1]
        Assert.assertEquals(expectedValue, actualValue)


    }
}