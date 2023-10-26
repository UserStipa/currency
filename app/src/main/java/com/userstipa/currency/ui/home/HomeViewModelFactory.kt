package com.userstipa.currency.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.userstipa.currency.App

class HomeViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val application = checkNotNull(extras[APPLICATION_KEY])
        val getMyCurrencies = (application as App).appComponent.useCaseGetMyCurrencies()
        val getNewCurrencies = application.appComponent.useCaseGetNewCurrencies()
        val dispatcherProvider = application.appComponent.getDispatcherProvider()
        return HomeViewModel(getMyCurrencies, getNewCurrencies, dispatcherProvider) as T
    }
}