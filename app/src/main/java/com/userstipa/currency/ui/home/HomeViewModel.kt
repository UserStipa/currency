package com.userstipa.currency.ui.home

import androidx.lifecycle.ViewModel
import com.userstipa.currency.domain.usecases.get_remote_currencies.GetRemoteCurrencies
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getRemoteCurrencies: GetRemoteCurrencies
) : ViewModel() {

}