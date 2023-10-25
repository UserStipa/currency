package com.userstipa.currency.domain.usecases.add_currency

import com.userstipa.currency.data.local.PreferencesKeys
import com.userstipa.currency.domain.model.Currency
import com.userstipa.currency.testUtil.RepositoryFake
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class AddCurrencyTest {

    private lateinit var addCurrency: AddCurrencyImpl
    private lateinit var repositoryFake: RepositoryFake

    @Before
    fun setUp() {
        repositoryFake = RepositoryFake()
        addCurrency = AddCurrencyImpl(repositoryFake)
    }

    @Test
    fun `launch - preferences with values`() = runTest {
        val currentCurrencies = setOf("bitcoin1", "bitcoin2")
        repositoryFake.setPreferences(PreferencesKeys.MY_CURRENCIES, currentCurrencies)

        val newCurrency = Currency(
            id = "bitcoin3",
            name = "Bitcoin_3",
            symbol = "BTC",
            isEnableCheckbox = false
        )
        addCurrency.launch(newCurrency)

        val expectedValue = setOf("bitcoin1", "bitcoin2", "bitcoin3")
        val actualValue = repositoryFake.getPreferences(PreferencesKeys.MY_CURRENCIES)
        Assert.assertEquals(expectedValue, actualValue)
    }

    @Test
    fun `launch - preferences without values`() = runTest {
        val newCurrency = Currency(
            id = "bitcoin3",
            name = "Bitcoin_3",
            symbol = "BTC",
            isEnableCheckbox = false
        )
        addCurrency.launch(newCurrency)

        val expectedValue = setOf("bitcoin3")
        val actualValue = repositoryFake.getPreferences(PreferencesKeys.MY_CURRENCIES)
        Assert.assertEquals(expectedValue, actualValue)
    }
}