package com.userstipa.currency.domain.usecases.remove_currency

import com.userstipa.currency.data.local.PreferencesKeys
import com.userstipa.currency.domain.model.Currency
import com.userstipa.currency.domain.repository.RepositoryFake
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RemoveCurrencyTest {

    private lateinit var removeCurrency: RemoveCurrency
    private lateinit var repositoryFake: RepositoryFake

    @Before
    fun setUp() {
        repositoryFake = RepositoryFake()
        removeCurrency = RemoveCurrencyImpl(repositoryFake)
    }

    @Test
    fun launch() = runTest {
        repositoryFake.setPreferences(
            PreferencesKeys.MY_CURRENCIES,
            setOf("bitcoin", "ethereum", "tether")
        )
        val currency = Currency(
            id = "bitcoin",
            name = "Bitcoin",
            symbol = "BTC",
            isEnableCheckbox = false
        )
        removeCurrency.launch(currency)

        val expectedValue = setOf("ethereum", "tether")
        val actualValue = repositoryFake.getPreferences(PreferencesKeys.MY_CURRENCIES)
        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun `launch - value not exists`() = runTest {
        repositoryFake.setPreferences(PreferencesKeys.MY_CURRENCIES, setOf("ethereum", "tether"))
        val currency = Currency(
            id = "bitcoin",
            name = "Bitcoin",
            symbol = "BTC",
            isEnableCheckbox = false
        )
        removeCurrency.launch(currency)

        val expectedValue = setOf("ethereum", "tether")
        val actualValue = repositoryFake.getPreferences(PreferencesKeys.MY_CURRENCIES)
        assertEquals(expectedValue, actualValue)
    }
}