package com.userstipa.currency.domain.usecases.get_my_currencies

import com.userstipa.currency.data.api.CurrencyDto
import com.userstipa.currency.data.api.GetCurrenciesDto
import com.userstipa.currency.data.local.PreferencesKeys
import com.userstipa.currency.domain.Resource
import com.userstipa.currency.domain.mapper.CurrencyPriceDetailMapper
import com.userstipa.currency.domain.model.CurrencyPrice
import com.userstipa.currency.domain.model.CurrencyPriceDetail
import com.userstipa.currency.testUtil.RepositoryFake
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class GetMyCurrenciesTest {

    private lateinit var getMyCurrenciesImpl: GetMyCurrenciesImpl
    private lateinit var repositoryFake: RepositoryFake

    @Before
    fun setUp() {
        repositoryFake = RepositoryFake()
        getMyCurrenciesImpl = GetMyCurrenciesImpl(repositoryFake, CurrencyPriceDetailMapper())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun launch() = runTest {
        repositoryFake.setPreferences(PreferencesKeys.MY_CURRENCIES, setOf("bitcoin"))
        repositoryFake.result = Response.success(
            GetCurrenciesDto(
                data = listOf(
                    CurrencyDto(
                        changePercent24Hr = "-1.1119214668658208",
                        explorer = "https://blockchain.info/",
                        id = "bitcoin",
                        marketCapUsd = "669720933191.1164966118771744",
                        maxSupply = "21000000.0000000000000000",
                        name = "Bitcoin",
                        priceUsd = 34312.7928147515751837,
                        rank = "1",
                        supply = "19518112.0000000000000000",
                        symbol = "BTC",
                        volumeUsd24Hr = "9567526018.0479966968956894",
                        vwap24Hr = "34043.8912981619835247"
                    )
                ),
                timestamp = "000000"
            )
        )
        val values = mutableListOf<Resource<List<CurrencyPriceDetail>>>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            getMyCurrenciesImpl.launch().toList(values)
        }

        val expectedValueLoading = Resource.Loading<List<CurrencyPrice>>()
        val expectedValueResult = Resource.Success(
            data = listOf(
                CurrencyPriceDetail(
                    id = "bitcoin",
                    name = "Bitcoin",
                    symbol = "BTC",
                    priceUsd = "34 312,79",
                    isEnableCheckbox = false
                )
            )
        )

        Assert.assertEquals(expectedValueLoading.javaClass.name, values[0].javaClass.name)
        Assert.assertEquals(expectedValueResult, values[1])

        val expectedValue = "bitcoin"
        Assert.assertEquals(expectedValue, repositoryFake.ids)
    }

    @Test
    fun `launch - correct arguments`() = runTest {
        repositoryFake.setPreferences(
            PreferencesKeys.MY_CURRENCIES,
            setOf("vechain", "bitcoin", "ethereum")
        )
        getMyCurrenciesImpl.launch().collect()
        val expectedValue = "vechain,bitcoin,ethereum"
        Assert.assertEquals(expectedValue, repositoryFake.ids)
    }
}