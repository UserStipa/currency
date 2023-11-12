package com.userstipa.currency.domain.usecases.get_currency_price_details

import com.userstipa.currency.data.api.get_currencies.CurrencyDto
import com.userstipa.currency.data.api.get_currencies.WrapperCurrenciesDto
import com.userstipa.currency.data.api.get_currency_history.PriceTimeDto
import com.userstipa.currency.data.api.get_currency_history.WrapperPriceTimeDto
import com.userstipa.currency.domain.mapper.MapperCurrencyPriceDetails
import com.userstipa.currency.domain.mapper.MapperPriceTimeFake
import com.userstipa.currency.domain.model.CurrencyPriceDetails
import com.userstipa.currency.domain.model.HistoryRange
import com.userstipa.currency.domain.model.PriceTime
import com.userstipa.currency.domain.repository.RepositoryFake
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.time.ZonedDateTime

class GetCurrencyPriceDetailsTest {

    private lateinit var repositoryFake: RepositoryFake
    private lateinit var mapperPriceTimeFake: MapperPriceTimeFake
    private lateinit var getCurrencyPriceDetails: GetCurrencyPriceDetailsImpl
    private lateinit var filterHistory: FilterHistoryFake

    @Before
    fun setUp() {
        repositoryFake = RepositoryFake()
        mapperPriceTimeFake = MapperPriceTimeFake()
        filterHistory = FilterHistoryFake()
        val mapperCurrencyPriceDetails = MapperCurrencyPriceDetails()
        getCurrencyPriceDetails = GetCurrencyPriceDetailsImpl(
            repositoryFake,
            mapperCurrencyPriceDetails,
            mapperPriceTimeFake,
            filterHistory
        )
    }

    @Test
    fun `get currency details - successful`() = runTest {
        val currencyId = "bitcoin"
        val historyRange = HistoryRange.LAST_HOUR
        val currentTestTime = ZonedDateTime.parse("2023-11-09T22:32Z[UTC]")
        val remoteCurrencies = Response.success(
            WrapperCurrenciesDto(
                data = listOf(
                    CurrencyDto(
                        changePercent24Hr = 1.1119214668658208,
                        explorer = "https://blockchain.info/",
                        id = "bitcoin",
                        marketCapUsd = 669720933191.1164966118771744,
                        maxSupply = 21000000.0000000000000000,
                        name = "Bitcoin",
                        priceUsd = 34312.7928147515751837,
                        rank = "1",
                        supply = 19518112.0000000000000000,
                        symbol = "BTC",
                        volumeUsd24Hr = "9567526018.0479966968956894",
                        vwap24Hr = 34043.8912981619835247
                    )
                ),
                timestamp = "000000"
            )
        )
        val remoteCurrencyHistory = Response.success(
            WrapperPriceTimeDto(
                data = listOf(
                    PriceTimeDto( //This Price is ignored by history range
                        priceUsd = 10.0,
                        time = 1699440565000,
                        circulatingSupply = "19537718.0000000000000000",
                        date = "2023-11-09T22:29:00.000Z"
                    ),
                    PriceTimeDto(
                        priceUsd = 36600.7604094079461288,
                        time = 1699568940000,
                        circulatingSupply = "19537718.0000000000000000",
                        date = "2023-11-09T22:29:00.000Z"
                    ),
                    PriceTimeDto(
                        priceUsd = 36609.5517245372621279,
                        time = 1699569000000,
                        circulatingSupply = "19537718.0000000000000000",
                        date = "2023-11-09T22:30:00.000Z"
                    ),
                    PriceTimeDto(
                        priceUsd = 36611.4766124757479815,
                        time = 1699569060000,
                        circulatingSupply = "19537718.0000000000000000",
                        date = "2023-11-09T22:31:00.000Z"
                    ),
                    PriceTimeDto(
                        priceUsd = 36596.3566120073129944,
                        time = 1699569120000,
                        circulatingSupply = "19537718.0000000000000000",
                        date = "2023-11-09T22:32:00.000Z"
                    )
                ),
                timestamp = 8903842098
            )
        )
        val expectedValue = CurrencyPriceDetails(
            changePercent24Hr = "1.12",
            explorer = "https://blockchain.info/",
            id = "bitcoin",
            marketCapUsd = "669 720 933 191,12 $",
            maxSupply = "21 000 000,00",
            name = "Bitcoin",
            priceUsdFormatted = "34 312,79 $",
            rank = "1",
            supply = "19 518 112,00",
            symbol = "BTC",
            volumeUsd24Hr = "9567526018.0479966968956894",
            vwap24Hr = "34 043,89 $",
            maxPriceUsdFormatted = "36 611,48 $",
            minPriceUsdFormatted = "36 596,36 $",
            isPositiveChangePercent24Hr = true,
            history = listOf(
                PriceTime(
                    priceUsd = 36600.7604094079461288,
                    priceUsdFormatted = "36 600,76 $",
                    dateTime = ZonedDateTime.parse("2023-11-09T22:29Z[UTC]"),
                    dateTimeFormatted = "2023-11-09T22:29:00.000Z"
                ),
                PriceTime(
                    priceUsd = 36609.5517245372621279,
                    priceUsdFormatted = "36 609,55 $",
                    dateTime = ZonedDateTime.parse("2023-11-09T22:30Z[UTC]"),
                    dateTimeFormatted = "2023-11-09T22:30:00.000Z"
                ),
                PriceTime(
                    priceUsd = 36611.4766124757479815,
                    priceUsdFormatted = "36 611,48 $",
                    dateTime = ZonedDateTime.parse("2023-11-09T22:31Z[UTC]"),
                    dateTimeFormatted = "2023-11-09T22:31:00.000Z"
                ),
                PriceTime(
                    priceUsd = 36596.3566120073129944,
                    priceUsdFormatted = "36 596,36 $",
                    dateTime = ZonedDateTime.parse("2023-11-09T22:32Z[UTC]"),
                    dateTimeFormatted = "2023-11-09T22:32:00.000Z"
                )
            )
        )

        repositoryFake.getRemoteCurrenciesResult = remoteCurrencies
        repositoryFake.getRemoteCurrencyHistoryResult = remoteCurrencyHistory
        filterHistory.currentTimeTest = currentTestTime
        val actualValue = getCurrencyPriceDetails.launch(currencyId, historyRange).first()

        Assert.assertEquals(expectedValue, actualValue)
    }

    @Test
    fun `get currency details - repository exception`() = runTest {
        val currencyId = "bitcoin"
        val historyRange = HistoryRange.LAST_HOUR
        val expectedException = Exception("Test error from repository")

        repositoryFake.getRemoteCurrenciesException = expectedException
        val flow = getCurrencyPriceDetails.launch(currencyId, historyRange)

        flow.catch { actualException ->
            Assert.assertEquals(expectedException, actualException)
        }
    }

    @Test
    fun `get currency details - mapper exception`() = runTest {
        val currencyId = "bitcoin"
        val historyRange = HistoryRange.LAST_HOUR
        val expectedException = Exception("Test error from mapper")

        mapperPriceTimeFake.mapException = expectedException
        val flow = getCurrencyPriceDetails.launch(currencyId, historyRange)

        flow.catch { actualException ->
            Assert.assertEquals(expectedException, actualException)
        }
    }
}