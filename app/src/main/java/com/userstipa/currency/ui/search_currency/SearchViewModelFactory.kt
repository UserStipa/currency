package com.userstipa.currency.ui.search_currency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.userstipa.currency.App

class SearchViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val application =
            checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
        val getRemoteCurrencies = (application as App).appComponent.getRemoteCurrencies()
        val dispatcherProvider = application.appComponent.getDispatcherProvider()
        return SearchViewModel(getRemoteCurrencies, dispatcherProvider) as T
    }

}