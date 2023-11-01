package com.userstipa.currency.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.userstipa.currency.App

class HomeViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val application = checkNotNull(extras[APPLICATION_KEY])
        val subscribeMyCurrencies = (application as App).appComponent.useCaseSubscribeMyCurrencies()
        val dispatcherProvider = application.appComponent.getDispatcherProvider()
        return HomeViewModel(subscribeMyCurrencies, dispatcherProvider) as T
    }
}