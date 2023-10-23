package com.userstipa.currency.domain.usecases.get_remote_currencies

import com.userstipa.currency.domain.Resource
import com.userstipa.currency.domain.model.Currency
import kotlinx.coroutines.flow.Flow

interface GetRemoteCurrencies {

    fun launch(): Flow<Resource<List<Currency>>>

}