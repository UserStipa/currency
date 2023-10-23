package com.userstipa.currency.domain.usecases.get_remote_currencies

import com.userstipa.currency.data.api.CurrencyDto
import com.userstipa.currency.data.repository.Repository
import com.userstipa.currency.domain.Resource
import com.userstipa.currency.domain.mapper.Mapper
import com.userstipa.currency.domain.model.Currency
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRemoteCurrenciesImpl @Inject constructor(
    private val repository: Repository,
    private val mapper: Mapper<CurrencyDto, Currency>
) : GetRemoteCurrencies {

    override fun launch(): Flow<Resource<List<Currency>>> = flow {
        try {
            emit(Resource.Loading())
            val networkResult = repository.getRemoteCurrencies()
            val data = networkResult.body()!!.data
            val result = mapper.map(data)
            emit(Resource.Success(result))
        } catch (e: Throwable) {
            emit(Resource.Error(e))
        }
    }

}