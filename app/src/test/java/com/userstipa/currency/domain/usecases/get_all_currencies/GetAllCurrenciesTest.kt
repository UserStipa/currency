package com.userstipa.currency.domain.usecases.get_all_currencies

import com.userstipa.currency.data.api.CurrencyDto
import com.userstipa.currency.data.api.GetCurrenciesDto
import com.userstipa.currency.data.local.PreferencesKeys
import com.userstipa.currency.domain.mapper.MapperCurrency
import com.userstipa.currency.domain.model.Currency
import com.userstipa.currency.domain.repository.RepositoryFake
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class GetAllCurrenciesTest {

    private lateinit var getAllCurrenciesImpl: GetAllCurrenciesImpl
    private lateinit var repositoryFake: RepositoryFake

    @Before
    fun setUp() {
        repositoryFake = RepositoryFake()
        getAllCurrenciesImpl = GetAllCurrenciesImpl(repositoryFake, MapperCurrency())
    }

    @Test
    fun launch() = runTest {
        val allCurrencies = Response.success(
            GetCurrenciesDto(
                data = listOf(
                    CurrencyDto(
                        changePercent24Hr = 1.1119214668658208,
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
                    ),
                    CurrencyDto(
                        changePercent24Hr = 3.3659161263923668,
                        explorer = "https://etherscan.io/",
                        id = "ethereum",
                        marketCapUsd = "214877322599.5196305515861831",
                        maxSupply = "null",
                        name = "Ethereum",
                        priceUsd = 1786.7197598854497559,
                        rank = "2",
                        supply = "120263584.3761507700000000",
                        symbol = "ETH",
                        volumeUsd24Hr = "4660708810.0445978384727955",
                        vwap24Hr = "1793.3749382772015356"
                    )
                ),
                timestamp = "00000"
            )
        )
        val expectedValue = listOf(
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

        repositoryFake.setPreferences(PreferencesKeys.MY_CURRENCIES, setOf("bitcoin"))
        repositoryFake.getRemoteCurrenciesResult = allCurrencies
        val actualValue = getAllCurrenciesImpl.launch().first()

        Assert.assertEquals(expectedValue, actualValue)
    }
}