package com.userstipa.currency.domain.usecases.get_my_currencies

import com.userstipa.currency.data.api.CurrencyDto
import com.userstipa.currency.data.api.GetCurrenciesDto
import com.userstipa.currency.data.local.PreferencesKeys
import com.userstipa.currency.domain.mapper.MapperCurrencyPriceDetail
import com.userstipa.currency.domain.model.CurrencyPriceDetail
import com.userstipa.currency.domain.repository.RepositoryFake
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
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
        getMyCurrenciesImpl = GetMyCurrenciesImpl(repositoryFake, MapperCurrencyPriceDetail())
    }

    @Test
    fun `get my currencies - successful`() = runTest {
        val myCurrenciesIds = setOf("bitcoin")
        val remoteCurrencies = Response.success(
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
                    )
                ),
                timestamp = "000000"
            )
        )
        val expectedValue = listOf(
            CurrencyPriceDetail(
                id = "bitcoin",
                name = "Bitcoin",
                symbol = "BTC",
                priceUsd = "34 312,79",
                changePercent24Hr = "1.12",
                isEnableCheckbox = false
            )
        )

        repositoryFake.setPreferences(PreferencesKeys.MY_CURRENCIES, myCurrenciesIds)
        repositoryFake.getRemoteCurrenciesResult = remoteCurrencies
        val actualValue = getMyCurrenciesImpl.launch().first()

        Assert.assertEquals(expectedValue, actualValue)
    }

    @Test
    fun `get my currencies - error`() = runTest {
        val myCurrenciesIds = setOf("bitcoin")
        val expectedValue = Exception("Test error")

        repositoryFake.setPreferences(PreferencesKeys.MY_CURRENCIES, myCurrenciesIds)
        repositoryFake.getRemoteCurrenciesException = expectedValue

        getMyCurrenciesImpl.launch().catch { actualException ->
            Assert.assertEquals(expectedValue, actualException)
        }
    }

    @Test
    fun `launch - empty myCurrenciesIds`() = runTest {
        val myCurrenciesIds = emptySet<String>()
        val expectedValue = emptyList<CurrencyPriceDetail>()

        repositoryFake.setPreferences(PreferencesKeys.MY_CURRENCIES, myCurrenciesIds)
        val actualValue = getMyCurrenciesImpl.launch().first()

        Assert.assertEquals(expectedValue, actualValue)
    }
}